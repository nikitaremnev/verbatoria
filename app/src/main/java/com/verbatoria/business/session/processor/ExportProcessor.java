package com.verbatoria.business.session.processor;

import com.verbatoria.data.repositories.session.ISessionRepository;
import java.util.Map;

import io.reactivex.Completable;

/**
 * Процессор для генерации отчета
 *
 * @author nikitaremnev
 */
public class ExportProcessor {

    private ISessionRepository mSessionRepository;

    public ExportProcessor(ISessionRepository sessionRepository, String applicationVersion) {
        mSessionRepository = sessionRepository;

    }

    public Completable getAllMeasurements(Map<String, String> answers) {
        return Completable.fromObservable(mSessionRepository.getAllMeasurements()
                .doOnNext(baseMeasurements -> {
//                    Collections.sort(baseMeasurements, new BaseMeasurementComparator());
//                    writeToJsonFile(baseMeasurements, answers);
                }));
    }

}
