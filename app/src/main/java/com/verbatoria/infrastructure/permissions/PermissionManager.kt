package com.verbatoria.infrastructure.permissions

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * @author n.remnev
 */

interface PermissionManager {

    fun checkPermissions(
        activity: AppCompatActivity,
        permissions: Array<String> = RequiredPermissions.getPermissions(),
        callback: () -> Unit
    )

}

class PermissionManagerImpl : PermissionManager {

    override fun checkPermissions(
        activity: AppCompatActivity,
        permissions: Array<String>,
        callback: () -> Unit
    ) {
        RxPermissions(activity)
            .request(*permissions)
            .subscribe { granted ->
                if (granted) {
                    callback()
                } else {
                    openPermissionsSettings(activity)
                }
            }
    }

    private fun openPermissionsSettings(context: Context) {
        context.startActivity(
            RequiredPermissions.createPermissionsIntent(
                context.packageName
            )
        )
    }
}