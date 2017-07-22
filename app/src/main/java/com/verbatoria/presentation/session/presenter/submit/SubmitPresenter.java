package com.verbatoria.presentation.session.presenter.submit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.neurosky.connection.ConnectionStates;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.presentation.session.presenter.writing.IWritingPresenter;
import com.verbatoria.presentation.session.view.submit.ISubmitView;
import com.verbatoria.presentation.session.view.writing.ActivityButtonState;
import com.verbatoria.presentation.session.view.writing.IWritingView;

import javax.inject.Inject;

import static com.verbatoria.business.session.activities.ActivitiesCodes.CODE_11;
import static com.verbatoria.business.session.activities.ActivitiesCodes.CODE_21;
import static com.verbatoria.business.session.activities.ActivitiesCodes.CODE_31;
import static com.verbatoria.business.session.activities.ActivitiesCodes.CODE_41;
import static com.verbatoria.business.session.activities.ActivitiesCodes.CODE_51;
import static com.verbatoria.business.session.activities.ActivitiesCodes.CODE_61;
import static com.verbatoria.business.session.activities.ActivitiesCodes.CODE_71;
import static com.verbatoria.business.session.activities.ActivitiesCodes.CODE_99;
import static com.verbatoria.business.session.activities.ActivitiesCodes.NO_CODE;

/**
 * Реализация презентера для экрана отправки результатов
 *
 * @author nikitaremnev
 */
public class SubmitPresenter implements ISubmitPresenter {

    private static final String TAG = SubmitPresenter.class.getSimpleName();

    @Inject
    public Context mContext;

    private ISessionInteractor mSessionInteractor;
    private ISubmitView mSubmitView;

    public SubmitPresenter(ISessionInteractor sessionInteractor) {
        this.mSessionInteractor = sessionInteractor;
    }

    @Override
    public void bindView(@NonNull ISubmitView submitView) {
        mSubmitView = submitView;
    }

    @Override
    public void unbindView() {
        mSubmitView = null;
        mSessionInteractor.dropCallbacks();
    }

}
