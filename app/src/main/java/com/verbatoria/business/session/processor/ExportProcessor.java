package com.verbatoria.business.session.processor;

import com.verbatoria.data.network.request.MeasurementRequestModel;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.session.comparator.BaseMeasurementComparator;
import com.verbatoria.data.repositories.session.model.AttentionMeasurement;
import com.verbatoria.data.repositories.session.model.BaseMeasurement;
import com.verbatoria.data.repositories.session.model.EEGMeasurement;
import com.verbatoria.data.repositories.session.model.EventMeasurement;
import com.verbatoria.data.repositories.session.model.MediationMeasurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import rx.Observable;

/**
 * Процессор для генерации отчета
 *
 * @author nikitaremnev
 */
public class ExportProcessor {

    private static final String TAG = ExportProcessor.class.getSimpleName();

    ISessionRepository mSessionRepository;

    public ExportProcessor(ISessionRepository sessionRepository) {
        mSessionRepository = sessionRepository;
    }

    public Observable<List<MeasurementRequestModel>> getAllMeasurements() {
        return Observable.merge(mSessionRepository.getAttentionMeasurements(), mSessionRepository.getEEGMeasurements(),
                mSessionRepository.getEventMeasurements(), mSessionRepository.getMediationMeasurements())
                .map(baseMeasurements -> {
                    Collections.sort(baseMeasurements, new BaseMeasurementComparator());
                    return reduceList(baseMeasurements);
                });
    }

    private List<MeasurementRequestModel> reduceList(List<? extends BaseMeasurement> baseMeasurements) {
        List<MeasurementRequestModel> measurements = new ArrayList<>();
        BaseMeasurementIterator iterator = new BaseMeasurementIterator(baseMeasurements);
        while (iterator.hasNext()) {
            MeasurementRequestModel measurementRequestModel = new MeasurementRequestModel();
            BaseMeasurement currentMeasurement = iterator.next();
            setMeasurementRequestModelFields(measurementRequestModel, currentMeasurement);
            while (iterator.hasNext() && currentMeasurement.getTimestamp() == iterator.next().getTimestamp()) {
                setMeasurementRequestModelFields(measurementRequestModel, iterator.get());
            }
            iterator.back();
            measurements.add(measurementRequestModel);
        }
        return measurements;
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
            return mMeasurements.size() > mIndex;
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
                throw new IllegalStateException();
            }
            return mMeasurements.get(mIndex);
        }
    }

}
