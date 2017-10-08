package com.verbatoria.presentation.calendar.presenter.add.children;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.children.IChildrenInteractor;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.add.children.IChildrenView;

import java.util.Date;

import okhttp3.ResponseBody;

import static com.verbatoria.presentation.calendar.view.add.children.ChildrenActivity.EXTRA_CHILD_MODEL;
import static com.verbatoria.presentation.calendar.view.add.children.ChildrenActivity.EXTRA_CLIENT_ID;

/**
 * Реализация презентера для экрана данных о детях
 *
 * @author nikitaremnev
 */
public class ChildrenPresenter extends BasePresenter implements IChildrenPresenter {

    private IChildrenInteractor mChildrenInteractor;
    private IChildrenView mChildrenView;
    private ChildModel mChildModel;
    private String mClientId;
    private boolean mIsEditMode;

    public ChildrenPresenter(IChildrenInteractor childrenInteractor) {
        mChildrenInteractor = childrenInteractor;
    }

    @Override
    public void bindView(@NonNull IChildrenView childrenView) {
        mChildrenView = childrenView;
    }

    @Override
    public void unbindView() {
        mChildrenView = null;
    }

    @Override
    public void obtainChild(Intent intent) {
        mChildModel = intent.getParcelableExtra(EXTRA_CHILD_MODEL);
        if (mChildModel != null) {
            mIsEditMode = true;
        } else {
            mChildModel = new ChildModel();
        }
        mChildModel.setClientId(intent.getStringExtra(EXTRA_CLIENT_ID));
    }

    @Override
    public ChildModel getChildModel() {
        return mChildModel;
    }

    @Override
    public boolean isEditMode() {
        return mIsEditMode;
    }

    @Override
    public void createChild() {
        mChildModel.setName(mChildrenView.getChildName());
        addSubscription(mChildrenInteractor.addChild(mChildModel)
                .doOnSubscribe(() -> mChildrenView.showProgress())
                .doOnUnsubscribe(() -> mChildrenView.hideProgress())
                .subscribe(this::handleChildAddSuccess, this::handleChildRequestError));
    }

    @Override
    public void editChild() {
        mChildModel.setName(mChildrenView.getChildName());
        addSubscription(mChildrenInteractor.editChild(mChildModel)
                .doOnSubscribe(() -> mChildrenView.showProgress())
                .doOnUnsubscribe(() -> mChildrenView.hideProgress())
                .subscribe(this::handleChildEditSuccess, this::handleChildRequestError));
    }

    @Override
    public String getChildName() {
        return mChildModel != null ? mChildModel.getName() != null ? mChildModel.getName() : "" : "";
    }

    @Override
    public String getChildBirthday() {
        return mChildModel != null ? mChildModel.getBirthdayDateString() != null ? mChildModel.getBirthdayDateString() : "" : "";
    }

    @Override
    public void setChildBirthday(Date birthday) {
        mChildModel.setBirthday(birthday);
        mChildrenView.updateBirthday();
    }

    @Override
    public void saveState(Bundle outState) {

    }

    @Override
    public void restoreState(Bundle savedInstanceState) {

    }

    private void handleChildAddSuccess(ChildModel childModel) {
        mChildModel.setId(childModel.getId());
        mChildrenView.showChildAdded();
    }

    private void handleChildEditSuccess(ResponseBody responseBody) {
        mChildrenView.showChildEdited();
    }

    private void handleChildRequestError(Throwable throwable) {
        mChildrenView.showError(throwable.getMessage());
    }
}
