package com.verbatoria.ui.writing

import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */


class WritingPresenter(
    private val eventId: String
) : BasePresenter<WritingView>(), WritingView.Callback {

    init {

    }

    override fun onAttachView(view: WritingView) {
        super.onAttachView(view)

    }

    //region WritingView.Callback



    //endregion

}