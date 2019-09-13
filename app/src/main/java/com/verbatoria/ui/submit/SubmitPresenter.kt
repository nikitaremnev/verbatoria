package com.verbatoria.ui.submit

import com.verbatoria.business.submit.SubmitInteractor
import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

class SubmitPresenter(
    private val eventId: String,
    private val submitInteractor: SubmitInteractor
) : BasePresenter<SubmitView>(), SubmitView.Callback {

    override fun onAttachView(view: SubmitView) {
        super.onAttachView(view)
        submitInteractor.submitData(eventId)
            .doOnComplete {
                view.showSuccessDialog()
            }
            .subscribe(
                { submitProgress ->
                    view.setStatus(submitProgress.progressResourceId)
                }, { error ->
                    error.printStackTrace()
                    view.showErrorDialog()
                }
            )
            .let(::addDisposable)
    }

    //region SubmitView.Callback

    override fun onResultDialogDismissed() {
        view?.close()
    }

    //endregion

}