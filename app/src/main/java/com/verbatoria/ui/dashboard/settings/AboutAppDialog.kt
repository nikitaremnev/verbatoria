package com.verbatoria.ui.dashboard.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

private const val APP_VERSION_EXTRA = "app_version"
private const val ANDROID_VERSION_EXTRA = "android_version"

class AboutAppDialog : DialogFragment() {

    companion object {

        fun build(init: Builder.() -> Unit): AboutAppDialog = Builder(
            init
        ).build()

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val rootView = LayoutInflater.from(context).inflate(R.layout.dialog_about_app, null)
        val appVersionTextView: TextView = rootView.findViewById(R.id.app_version_title_text_view)
        val androidVersionTextView: TextView =
            rootView.findViewById(R.id.android_version_title_text_view)

        if (arguments?.containsKey(APP_VERSION_EXTRA) == true) {
            appVersionTextView.text = arguments?.getString(APP_VERSION_EXTRA)
        }
        if (arguments?.containsKey(ANDROID_VERSION_EXTRA) == true) {
            androidVersionTextView.text = arguments?.getString(ANDROID_VERSION_EXTRA)
        }

        return AlertDialog.Builder(activity)
            .setView(rootView)
            .setPositiveButton(R.string.ok, null)
            .create()
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        if (manager?.isStateSaved == false) {
            super.show(manager, tag)
        }
    }

    class Builder(init: Builder.() -> Unit) {

        var appVersion: String? = null

        var androidVersion: String? = null

        init {
            init()
        }

        fun build() =
            AboutAppDialog().apply {
                arguments = Bundle()
                    .also { args ->
                        if (appVersion != null) args.putString(
                            APP_VERSION_EXTRA,
                            appVersion
                        )
                        if (androidVersion != null) args.putString(
                            ANDROID_VERSION_EXTRA,
                            androidVersion
                        )
                        isCancelable = true
                    }
            }
    }

}