package com.verbatoria.presentation.login.presenter.recovery;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.login.view.recovery.IRecoveryView;

import static com.verbatoria.presentation.login.presenter.recovery.RecoveryPresenter.RecoveryPresenterState.EXTRA_STATE;
import static com.verbatoria.presentation.login.presenter.recovery.RecoveryPresenter.RecoveryPresenterState.STATE_INPUT_CODE;
import static com.verbatoria.presentation.login.presenter.recovery.RecoveryPresenter.RecoveryPresenterState.STATE_INPUT_PASSWORDS;
import static com.verbatoria.presentation.login.presenter.recovery.RecoveryPresenter.RecoveryPresenterState.STATE_INPUT_PHONE;

/**
 * Реализация презентера для восстановления пароля
 *
 * @author nikitaremnev
 */
public class RecoveryPresenter extends BasePresenter implements IRecoveryPresenter  {

    private static final String TAG = RecoveryPresenter.class.getSimpleName();

    private ILoginInteractor mLoginInteractor;
    private RecoveryPresenterState mPresenterState;
    private IRecoveryView mRecoveryView;

    public RecoveryPresenter(ILoginInteractor loginInteractor) {
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
    public void rememberPassword() {
        mRecoveryView.rememberPassword();
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
    public boolean confirmPassword(String password, String confirmedPassword) {
        return password.equals(confirmedPassword);
    }

    @Override
    public PasswordRequirements checkPasswordRequirements(String password) {
        if (TextUtils.isEmpty(password)) return PasswordRequirements.EMPTY;
        if (password.length() < 7) return PasswordRequirements.TOO_SHORT;
        if (password.equals("1234567") || password.equals("7654321")) return PasswordRequirements.TOO_EASY;
        return PasswordRequirements.OK;
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
