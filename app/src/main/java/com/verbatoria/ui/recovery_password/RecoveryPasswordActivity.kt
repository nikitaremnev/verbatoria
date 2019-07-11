package com.verbatoria.ui.recovery_password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.recovery_password.RecoveryPasswordComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView

/**
 * @author n.remnev
 */

interface RecoveryPasswordView : BaseView {

    interface Callback {

    }

}

class RecoveryPasswordActivity :
    BasePresenterActivity<RecoveryPasswordView, RecoveryPasswordPresenter, RecoveryPasswordActivity, RecoveryPasswordComponent>(),
    RecoveryPasswordView {

    companion object {

        fun createIntent(context: Context): Intent =
            Intent(context, RecoveryPasswordActivity::class.java)

    }

    private lateinit var phoneEditText: EditText
    private lateinit var confirmationCodeEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var repeatNewPasswordEditText: EditText

    private lateinit var clearPhoneImageView: ImageView
    private lateinit var clearConfirmationCodeImageView: ImageView
    private lateinit var clearNewPasswordImageView: ImageView
    private lateinit var clearRepeatNewPasswordImageView: ImageView

    private lateinit var progressBar: ProgressBar
    private lateinit var submitButton: Button

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_recovery_password

    override fun buildComponent(injector: Injector, savedState: Bundle?): RecoveryPasswordComponent =
        injector.plusRecoveryPasswordComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        phoneEditText = findViewById(R.id.phone_edit_text)
        confirmationCodeEditText = findViewById(R.id.confirmation_code_edit_text)
        newPasswordEditText = findViewById(R.id.new_password_edit_text)
        repeatNewPasswordEditText = findViewById(R.id.repeat_new_password_edit_text)
        clearPhoneImageView = findViewById(R.id.phone_clear_button)
        clearConfirmationCodeImageView = findViewById(R.id.confirmation_code_clear_button)
        clearNewPasswordImageView = findViewById(R.id.new_password_clear_button)
        clearRepeatNewPasswordImageView = findViewById(R.id.repeat_new_password_clear_button)
        progressBar = findViewById(R.id.progress_bar)
        submitButton = findViewById(R.id.submit_button)
    }

    //region RecoveryPasswordView

    //endregion

}


