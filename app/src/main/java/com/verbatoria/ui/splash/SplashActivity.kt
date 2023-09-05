package com.verbatoria.ui.splash;

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.splash.SplashComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.login.LoginActivity

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

    override fun onUserInteraction() {
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
            != PackageManager.PERMISSION_GRANTED ||
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED))
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ),
                    REQUEST_PERMISSION_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    REQUEST_PERMISSION_CODE
                )
            }
        } else {
            presenter.onPermissionsGranted()
        }
    }

    override fun openLogin() {
        startActivity(LoginActivity.createIntent(this))
        finish()
    }

    //endregion

}
