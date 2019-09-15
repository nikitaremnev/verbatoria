package com.verbatoria.ui.splash

import android.os.Handler
import com.verbatoria.infrastructure.file.FileUtil
import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

private const val OPEN_LOGIN_DELAY = 2000L

class SplashPresenter(
    fileUtil: FileUtil
) : BasePresenter<SplashView>(), SplashView.Callback {

    init {
        fileUtil.createApplicationDirectory()
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