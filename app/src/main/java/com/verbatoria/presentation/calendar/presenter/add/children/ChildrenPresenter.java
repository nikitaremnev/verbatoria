package com.verbatoria.presentation.calendar.presenter.add.children;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.verbatoria.business.children.IChildrenInteractor;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.data.network.response.MessageResponseModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.add.children.IChildrenView;

import java.util.Date;

import okhttp3.ResponseBody;

import static com.verbatoria.presentation.calendar.view.add.children.ChildrenActivity.EXTRA_CHILD_MODEL;

/**
 * Реализация презентера для экрана данных о детях
 *
 * @author nikitaremnev
 */
public class ChildrenPresenter extends BasePresenter implements IChildrenPresenter {

    private IChildrenInteractor mChildrenInteractor;
    private IChildrenView mChildrenView;
    private ChildModel mChildModel;
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
                .subscribe(this::handleChildRequestSuccess, this::handleChildRequestError));
    }

    @Override
    public void editChild() {
        mChildModel.setName(mChildrenView.getChildName());
        addSubscription(mChildrenInteractor.editChild(mChildModel)
                .doOnSubscribe(() -> mChildrenView.showProgress())
                .doOnUnsubscribe(() -> mChildrenView.hideProgress())
                .subscribe(this::handleChildRequestSuccess, this::handleChildRequestError));
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

    private void handleChildRequestSuccess(MessageResponseModel messageResponseModel) {
        mChildrenView.finishWithResult();
    }

    private void handleChildRequestSuccess(ResponseBody responseBody) {
        mChildrenView.finishWithResult();
    }

    private void handleChildRequestError(Throwable throwable) {
        mChildrenView.showError(throwable.getMessage());
    }
}
