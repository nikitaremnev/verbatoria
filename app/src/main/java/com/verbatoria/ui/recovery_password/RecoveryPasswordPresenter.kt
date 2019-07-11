package com.verbatoria.ui.recovery_password

import com.verbatoria.business.recovery_password.RecoveryPasswordInteractor
import com.verbatoria.ui.base.BasePresenter
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

class RecoveryPasswordPresenter(
    private val recoveryPasswordInteractor: RecoveryPasswordInteractor
) : BasePresenter<RecoveryPasswordView>(), RecoveryPasswordView.Callback {

    private val logger = LoggerFactory.getLogger("RecoveryPasswordPresenter")

    init {

    }

    override fun onAttachView(view: RecoveryPasswordView) {
        super.onAttachView(view)

    }

    //region RecoveryPasswordView.Callback

    //endregion

}