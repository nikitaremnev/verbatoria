package com.verbatoria.infrastructure.permissions

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * @author n.remnev
 */

private const val EXTRA_PACKAGE = "package"

object RequiredPermissions {

    fun getPermissions() =
        arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    @JvmStatic
    fun createPermissionsIntent(packageName: String) =
        Intent().apply {
            data = Uri.fromParts(EXTRA_PACKAGE, packageName, null)
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

}
