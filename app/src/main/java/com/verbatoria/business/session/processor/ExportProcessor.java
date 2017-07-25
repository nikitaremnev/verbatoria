package com.verbatoria.business.session.processor;

import com.verbatoria.business.session.SessionInteractorException;
import com.verbatoria.data.network.request.MeasurementRequestModel;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.comparator.BaseMeasurementComparator;
import com.verbatoria.data.repositories.session.model.AttentionMeasurement;
import com.verbatoria.data.repositories.session.model.BaseMeasurement;
import com.verbatoria.data.repositories.session.model.EEGMeasurement;
import com.verbatoria.data.repositories.session.model.EventMeasurement;
import com.verbatoria.data.repositories.session.model.MediationMeasurement;
import com.verbatoria.utils.FileUtils;
import com.verbatoria.utils.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Процессор для генерации отчета
 *
 * @author nikitaremnev
 */
public class ExportProcessor {

    private static final String TAG = ExportProcessor.class.getSimpleName();

    private ISessionRepository mSessionRepository;

    public ExportProcessor(ISessionRepository sessionRepository) {
        mSessionRepository = sessionRepository;
    }

    public Observable<Void> getAllMeasurements(Map<String, String> answers) {
        return Observable.merge(mSessionRepository.getAttentionMeasurements(), mSessionRepository.getEEGMeasurements(),
                mSessionRepository.getEventMeasurements(), mSessionRepository.getMediationMeasurements())
                .map(new Func1<List<? extends BaseMeasurement>, Void>() {
                    @Override
                    public Void call(List<? extends BaseMeasurement> baseMeasurements) {
                        Collections.sort(baseMeasurements, new BaseMeasurementComparator());
                        writeToJsonFile(baseMeasurements, answers);
                        return null;
                    }
                });
    }

    private void writeToJsonFile(List<? extends BaseMeasurement> baseMeasurements, Map<String, String> answers) {
        FileOutputStream outStream = null;
        OutputStreamWriter outStreamWriter = null;
        try {
            File reportFile = new File(FileUtils.getApplicationDirectory(), "report" + FileUtils.JSON_FILE_EXTENSION);
            if (!reportFile.exists()) {
                reportFile.createNewFile();
            }
            outStream = new FileOutputStream(reportFile);
            outStreamWriter = new OutputStreamWriter(outStream);

            outStreamWriter.append("[");

            BaseMeasurementIterator iterator = new BaseMeasurementIterator(baseMeasurements);
            while (iterator.hasNext()) {
                MeasurementRequestModel measurementRequestModel = new MeasurementRequestModel();
                BaseMeasurement currentMeasurement = iterator.next();
                setMeasurementRequestModelFields(measurementRequestModel, currentMeasurement);
                while (iterator.hasNext() && currentMeasurement.getTimestamp() == iterator.next().getTimestamp()) {
                    setMeasurementRequestModelFields(measurementRequestModel, iterator.get());
                }
                iterator.back();
                outStreamWriter.append(measurementRequestModel.toString());
            }
            outStreamWriter.append("]");

            outStream.flush();
            outStreamWriter.flush();
            outStream.close();
            outStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SessionInteractorException("File exception");
        }
    }

    private void setAnswers(List<MeasurementRequestModel> measurements, Map<String, String> answers) {
        for (String key : answers.keySet()) {
            int position = Integer.parseInt(key);
            measurements.get(position).setReserveBlank2(answers.get(key));
        }
    }

    private void setMeasurementRequestModelFields(MeasurementRequestModel measurementRequestModel, BaseMeasurement baseMeasurement) {
        if (baseMeasurement instanceof AttentionMeasurement) {
            setAttentionFields(measurementRequestModel, (AttentionMeasurement) baseMeasurement);
        }
        if (baseMeasurement instanceof MediationMeasurement) {
            setMediationFields(measurementRequestModel, (MediationMeasurement) baseMeasurement);
        }
        if (baseMeasurement instanceof EEGMeasurement) {
            setEEGFields(measurementRequestModel, (EEGMeasurement) baseMeasurement);
        }
        if (baseMeasurement instanceof EventMeasurement) {
            setEventFields(measurementRequestModel, (EventMeasurement) baseMeasurement);
        }
    }

    private void setAttentionFields(MeasurementRequestModel measurementRequestModel,
                                    AttentionMeasurement attentionMeasurement) {
        measurementRequestModel.setAttentionValue(attentionMeasurement.getAttentionValue());
    }

    private void setMediationFields(MeasurementRequestModel measurementRequestModel,
                                    MediationMeasurement mediationMeasurement) {
        measurementRequestModel.setMediationValue(mediationMeasurement.getMediationValue());
    }

    private void setEEGFields(MeasurementRequestModel measurementRequestModel,
                              EEGMeasurement eegMeasurement) {
        measurementRequestModel.setDeltaValue(eegMeasurement.getDeltaValue());
        measurementRequestModel.setThetaValue(eegMeasurement.getThetaValue());
        measurementRequestModel.setLowAlphaValue(eegMeasurement.getLowAlphaValue());
        measurementRequestModel.setHighAlphaValue(eegMeasurement.getHighAlphaValue());
        measurementRequestModel.setLowBetaValue(eegMeasurement.getLowBetaValue());
        measurementRequestModel.setHighBetaValue(eegMeasurement.getHighBetaValue());
        measurementRequestModel.setLowGammaValue(eegMeasurement.getLowGammaValue());
        measurementRequestModel.setMidGammaValue(eegMeasurement.getMidGammaValue());
    }

    private void setEventFields(MeasurementRequestModel measurementRequestModel,
                                EventMeasurement eventMeasurement) {
        measurementRequestModel.setActionId(eventMeasurement.getActivityCode());
    }

    private class BaseMeasurementIterator implements Iterator<BaseMeasurement> {

        private int mIndex = -1;
        private List<? extends BaseMeasurement> mMeasurements;

        public BaseMeasurementIterator(List<? extends BaseMeasurement> measurements) {
            mMeasurements = measurements;
        }

        @Override
        public boolean hasNext() {
            return mMeasurements.size() > (mIndex + 1);
        }

        @Override
        public BaseMeasurement next() {
            if (hasNext()) {
                mIndex += 1;
                return mMeasurements.get(mIndex);
            }
            return null;
        }

        @Override
        public void remove() {
            if (mIndex < 0 || mIndex >= mMeasurements.size()) {
                Logger.e(TAG, "remove() exception");
                throw new IllegalStateException();
            }
            mMeasurements.remove(mIndex);
        }

        public void back() {
            if (mIndex < 0) {
                throw new IllegalStateException();
            }
            mIndex --;
        }

        public BaseMeasurement get() {
            if (mIndex < 0 || mIndex >= mMeasurements.size()) {
                Logger.e(TAG, "get() exception");
                throw new IllegalStateException();
            }
            return mMeasurements.get(mIndex);
        }
    }

}
