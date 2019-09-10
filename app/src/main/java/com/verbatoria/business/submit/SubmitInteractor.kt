package com.verbatoria.business.submit

import com.verbatoria.domain.activities.manager.ActivitiesManager
import com.verbatoria.domain.activities.model.ActivityCode
import com.verbatoria.domain.activities.model.GroupedActivities
import com.verbatoria.domain.bci_data.manager.BCIDataManager
import com.verbatoria.domain.bci_data.model.BCIData
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface SubmitInteractor {



}

class SubmitInteractorImpl(
    private val activitiesManager: ActivitiesManager,
    private val bciDataManager: BCIDataManager,
    private val quest
    private val schedulersFactory: RxSchedulersFactory
) : SubmitInteractor {


}