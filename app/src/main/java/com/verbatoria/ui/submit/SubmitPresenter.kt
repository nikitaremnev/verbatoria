package com.verbatoria.ui.submit

import com.verbatoria.business.submit.SubmitInteractor
import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

class SubmitPresenter(
    private val sessionId: String,
    private val submitInteractor: SubmitInteractor
) : BasePresenter<SubmitView>(), SubmitView.Callback {

    override fun onAttachView(view: SubmitView) {
        super.onAttachView(view)
        submitInteractor.submitData(sessionId)
            .doOnComplete {
                view.showSuccessDialog()
            }
            .subscribe(
                { submitProgress ->
                    view.setStatus(submitProgress.progressResourceId)
                }, { error ->
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