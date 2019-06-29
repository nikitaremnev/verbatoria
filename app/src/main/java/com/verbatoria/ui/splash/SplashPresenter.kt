package com.verbatoria.ui.splash

import android.os.Handler
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.utils.FileUtils

/**
 * @author n.remnev
 */

private const val OPEN_LOGIN_DELAY = 2000L

class SplashPresenter : BasePresenter<SplashView>(), SplashView.Callback {

    init {
        FileUtils.createApplicationDirectory()
    }

    override fun onAttachView(view: SplashView) {
        super.onAttachView(view)
        view.requestPermissions()
    }

    //region SplashView.Callback

    override fun onPermissionsGranted() {
        startLoginTimer()
    }

    override fun onPermissionsDenied() {
        view?.requestPermissions()
    }

    //endregion

    private fun startLoginTimer() {
        Handler().postDelayed(
            {
                view?.openLogin()
            },
            OPEN_LOGIN_DELAY
        )
    }

}