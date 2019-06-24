package com.verbatoria.presentation.splash;

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.remnev.verbatoria.R
import com.verbatoria.di.common.Injector
import com.verbatoria.di.splash.SplashComponent
import com.verbatoria.presentation.base.BasePresenterActivity
import com.verbatoria.presentation.base.BaseView
import com.verbatoria.presentation.login.view.login.LoginActivity

/**
 * @author nikitaremnev
 */

private const val REQUEST_PERMISSION_CODE = 2444

interface SplashView : BaseView {

    fun requestPermissions()

    fun openLogin()

    interface Callback {

        fun onPermissionsGranted()

        fun onPermissionsDenied()

    }

}

class SplashActivity : BasePresenterActivity<SplashView, SplashPresenter, SplashActivity, SplashComponent>(), SplashView {

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_splash

    override fun buildComponent(injector: Injector, savedState: Bundle?): SplashComponent =
        injector.plusSplashComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        //empty
    }

    //endregion

    //region AppCompatActivity

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            REQUEST_PERMISSION_CODE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.onPermissionsGranted()
                } else {
                    presenter.onPermissionsDenied()
                }
        }
    }

    //endregion

    //region SplashView

    override fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_PERMISSION_CODE
            )
        } else {
            presenter.onPermissionsGranted()
        }
    }

    override fun openLogin() {
        startActivity(LoginActivity.newInstance(this))
        finish()
    }

    //endregion

}
