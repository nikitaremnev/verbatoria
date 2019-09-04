package com.verbatoria.ui.writing

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.remnev.verbatoria.R
import com.remnev.verbatoria.R.*
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.invisible
import com.verbatoria.infrastructure.extensions.show

/**
 * @author n.remnev
 */

class BCIConnectionDialog : DialogFragment() {

    private var bciConnectionDialogClickListener: OnBCIConnectionDialogClickListener? = null

    private lateinit var instructionTextView: TextView

    private lateinit var progressBar: ProgressBar
    private lateinit var progressBarTextView: TextView

    private lateinit var connectionStateImageView: ImageView
    private lateinit var connectionStateTextView: TextView

    private lateinit var exitButton: Button
    private lateinit var startButton: Button
    private lateinit var connectButton: Button
    private lateinit var settingsButton: Button

    //region DialogFragment

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        bciConnectionDialogClickListener = activity as? OnBCIConnectionDialogClickListener

        val rootView = LayoutInflater.from(context).inflate(layout.dialog_bci_connection, null)

        instructionTextView = rootView.findViewById(R.id.connection_instruction_text_view)

        progressBar = rootView.findViewById(R.id.progress_bar)
        progressBarTextView = rootView.findViewById(R.id.progress_bar_text_view)

        connectionStateImageView = rootView.findViewById(R.id.connection_state_image_view)
        connectionStateTextView = rootView.findViewById(R.id.connection_state_text_view)

        exitButton = rootView.findViewById(R.id.exit_button)
        startButton = rootView.findViewById(R.id.start_button)
        connectButton = rootView.findViewById(R.id.connect_button)
        settingsButton = rootView.findViewById(R.id.settings_button)

        progressBar.hide()
        progressBarTextView.hide()

        exitButton.setOnClickListener {
            bciConnectionDialogClickListener?.onExitClicked(tag)
        }
        startButton.setOnClickListener {
            bciConnectionDialogClickListener?.onStartClicked(tag)
        }
        connectButton.setOnClickListener {
            bciConnectionDialogClickListener?.onConnectClicked(tag)
        }
        settingsButton.setOnClickListener {
            bciConnectionDialogClickListener?.onSettingsClicked(tag)
        }

        isCancelable = false

        return AlertDialog.Builder(activity)
            .setTitle(R.string.session_connection)
            .setView(rootView)
            .create()
    }

    override fun onDestroy() {
        bciConnectionDialogClickListener = null
        super.onDestroy()
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        if (manager?.isStateSaved == false) {
            super.show(manager, tag)
        }
    }

    //endregion

    //region logic methods

    fun showBluetoothDisabled() {
        instructionTextView.invisible()

        progressBar.hide()
        progressBarTextView.hide()

        connectionStateImageView.setImageResource(R.drawable.ic_neurointerface_error)
        connectionStateImageView.show()

        connectionStateTextView.setText(R.string.writing_connection_bluetooth_disabled)
        connectionStateTextView.show()

        startButton.hide()
        connectButton.hide()
        exitButton.show()
        settingsButton.show()
    }

    fun showConnecting() {
        instructionTextView.invisible()

        connectionStateImageView.hide()
        connectionStateTextView.hide()

        progressBar.show()
        progressBarTextView.show()

        startButton.hide()
        settingsButton.hide()
        connectButton.show()
        exitButton.show()
    }

    fun showConnectionError() {
        instructionTextView.invisible()

        progressBar.hide()
        progressBarTextView.hide()

        connectionStateImageView.setImageResource(R.drawable.ic_neurointerface_error)
        connectionStateImageView.show()

        connectionStateTextView.setText(R.string.session_connection_error)
        connectionStateTextView.show()

        startButton.hide()
        settingsButton.hide()
        connectButton.show()
        exitButton.show()
    }

    fun showConnected() {
        instructionTextView.invisible()

        progressBar.hide()
        progressBarTextView.hide()

        connectionStateImageView.setImageResource(R.drawable.ic_neurointerface_connected)
        connectionStateImageView.show()

        connectionStateTextView.setText(R.string.session_connected)
        connectionStateTextView.show()

        settingsButton.hide()
        connectButton.hide()
        exitButton.hide()
        startButton.show()
    }

    //endregion

    interface OnBCIConnectionDialogClickListener {

        fun onConnectClicked(tag: String?)

        fun onExitClicked(tag: String?)

        fun onStartClicked(tag: String?)

        fun onSettingsClicked(tag: String?)

    }

}