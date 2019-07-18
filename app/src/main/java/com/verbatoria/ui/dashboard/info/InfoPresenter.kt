package com.verbatoria.ui.dashboard.info

import com.verbatoria.ui.base.BasePresenter
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

class InfoPresenter : BasePresenter<InfoView>(), InfoView.Callback {

    private val logger = LoggerFactory.getLogger("InfoPresenter")

    init {

    }

    override fun onAttachView(view: InfoView) {
        super.onAttachView(view)

    }

    //region InfoView.Callback



    //endregion

}