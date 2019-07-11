package com.verbatoria.ui.recovery_password;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.verbatoria.business.login.AuthorizationInteractor;
import com.verbatoria.data.network.response.MessageResponseModel;
import com.verbatoria.infrastructure.BasePresenter;

import static com.verbatoria.ui.recovery_password.RecoveryPresenter.RecoveryPresenterState.EXTRA_STATE;
import static com.verbatoria.ui.recovery_password.RecoveryPresenter.RecoveryPresenterState.STATE_INPUT_CODE;
import static com.verbatoria.ui.recovery_password.RecoveryPresenter.RecoveryPresenterState.STATE_INPUT_PASSWORDS;
import static com.verbatoria.ui.recovery_password.RecoveryPresenter.RecoveryPresenterState.STATE_INPUT_PHONE;
import static com.verbatoria.ui.recovery_password.RecoveryActivity.EXTRA_PHONE;

/**
 * Реализация презентера для восстановления пароля
 *
 * @author nikitaremnev
 */
public class RecoveryPresenter extends BasePresenter implements IRecoveryPresenter  {

    private static final String TAG = RecoveryPresenter.class.getSimpleName();

    private AuthorizationInteractor mLoginInteractor;
    private RecoveryPresenterState mPresenterState;
    private IRecoveryView mRecoveryView;

    public RecoveryPresenter(AuthorizationInteractor loginInteractor) {
        this.mLoginInteractor = loginInteractor;
        mPresenterState = new RecoveryPresenterState();
    }

    @Override
    public void bindView(@NonNull IRecoveryView recoveryView) {
        this.mRecoveryView = recoveryView;
    }

    @Override
    public void unbindView() {
        mRecoveryView = null;
    }

    @Override
    public void obtainPhone(Intent intent) {
        String phone = intent.getStringExtra(EXTRA_PHONE);
        if (phone != null) {
            mRecoveryView.setPhone(phone);
        }
    }

    @Override
    public void rememberPassword() {
        mRecoveryView.rememberPassword();
    }

    @Override
    public void recoveryPassword() {
        mRecoveryView.showProgress();
        addSubscription(
                mLoginInteractor.recoveryPassword(mRecoveryView.getPhone())
                        .doOnComplete(() -> mRecoveryView.hideProgress())
                        .subscribe(this::handleSuccessRecoveryPassword, this::handleErrorRecoveryPassword)
        );
    }

    @Override
    public void sendNewPassword() {
        mRecoveryView.showProgress();
        addSubscription(
                mLoginInteractor.resetPassword(mRecoveryView.getPhone(), mRecoveryView.getCode(), mRecoveryView.getNewPassword())
                        .doOnComplete(() -> mRecoveryView.hideProgress())
                        .subscribe(this::handleSuccessResetPassword, this::handleErrorResetPassword)
        );
    }

    @Override
    public void saveState(Bundle outState) {
        outState.putParcelable(EXTRA_STATE, mPresenterState);
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPresenterState = savedInstanceState.getParcelable(EXTRA_STATE);
            switch (mPresenterState != null ? mPresenterState.state : 0) {
                case STATE_INPUT_PHONE:
                    mRecoveryView.showPhoneInput();
                    break;
                case STATE_INPUT_CODE:
                    mRecoveryView.showCodeInput();
                    break;
                case STATE_INPUT_PASSWORDS:
                    mRecoveryView.showNewPasswordInput();
                    break;
            }
        }
    }

    @Override
    public boolean confirmPassword() {
        return mRecoveryView.getNewPassword().equals(mRecoveryView.getNewConfirmPassword());
    }

    @Override
    public String getCountry() {
        return mLoginInteractor.getCountry();
    }

    @Override
    public PasswordRequirements checkPasswordRequirements() {
        String password = mRecoveryView.getNewPassword();
        if (TextUtils.isEmpty(password)) return PasswordRequirements.EMPTY;
        if (password.length() < 7) return PasswordRequirements.TOO_SHORT;
        if (password.equals("1234567") || password.equals("7654321"))
            return PasswordRequirements.TOO_EASY;
        return PasswordRequirements.OK;
    }

    private void handleSuccessRecoveryPassword(MessageResponseModel messageResponseModel) {
        Log.e(TAG, "handleSuccessRecoveryPassword: " + messageResponseModel.toString());
        mRecoveryView.showCodeInput();
    }

    private void handleErrorRecoveryPassword(Throwable throwable) {
        throwable.printStackTrace();
        mRecoveryView.showError(throwable.getMessage());
    }

    private void handleSuccessResetPassword(MessageResponseModel messageResponseModel) {
        Log.e(TAG, "handleSuccessResetPassword: " + messageResponseModel.toString());
        mRecoveryView.rememberPassword();
    }

    private void handleErrorResetPassword(Throwable throwable) {
        throwable.printStackTrace();
        mRecoveryView.showError(throwable.getMessage());
    }

    static class RecoveryPresenterState implements Parcelable {

        public static final int STATE_INPUT_PHONE = 0;
        public static final int STATE_INPUT_CODE = 1;
        public static final int STATE_INPUT_PASSWORDS = 2;

        public static final String EXTRA_STATE = RecoveryPresenterState.class.getSimpleName();

        private int state;

        public RecoveryPresenterState() {
            state = STATE_INPUT_PASSWORDS;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.state);
        }

        protected RecoveryPresenterState(Parcel in) {
            this.state = in.readInt();
        }

        public static final Parcelable.Creator<RecoveryPresenterState> CREATOR = new Parcelable.Creator<RecoveryPresenterState>() {
            @Override
            public RecoveryPresenterState createFromParcel(Parcel source) {
                return new RecoveryPresenterState(source);
            }

            @Override
            public RecoveryPresenterState[] newArray(int size) {
                return new RecoveryPresenterState[size];
            }
        };
    }

}
