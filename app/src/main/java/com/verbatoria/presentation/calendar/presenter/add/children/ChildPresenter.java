package com.verbatoria.presentation.calendar.presenter.add.children;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.remnev.verbatoriamini.R;
import com.verbatoria.business.children.IChildrenInteractor;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.add.children.IChildView;
import com.verbatoria.presentation.calendar.view.add.children.search.ISearchChildrenView;

import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;

import static com.verbatoria.presentation.calendar.view.add.children.ChildActivity.EXTRA_CHILD_MODEL;
import static com.verbatoria.presentation.calendar.view.add.children.ChildActivity.EXTRA_CLIENT_MODEL;
import static com.verbatoria.presentation.calendar.view.add.children.age.ChildAgeDialogFragment.START_AGE;

/**
 * Реализация презентера для экрана данных о детях
 *
 * @author nikitaremnev
 */
public class ChildPresenter extends BasePresenter implements IChildPresenter {

    private IChildrenInteractor mChildrenInteractor;
    private IChildView mChildrenView;
    private ISearchChildrenView mSearchChildrenView;
    private ChildModel mChildModel;
    private ClientModel mClientModel;
    private boolean mIsEditMode;

    public ChildPresenter(IChildrenInteractor childrenInteractor) {
        mChildrenInteractor = childrenInteractor;
    }

    @Override
    public void bindView(@NonNull ISearchChildrenView searchChildrenView) {
        mSearchChildrenView = searchChildrenView;
    }

    @Override
    public void bindView(@NonNull IChildView childrenView) {
        mChildrenView = childrenView;
    }

    @Override
    public void unbindView() {
        mSearchChildrenView = null;
        mChildrenView = null;
    }

    @Override
    public void obtainChild(Intent intent) {
        mClientModel = intent.getParcelableExtra(EXTRA_CLIENT_MODEL);
        mChildModel = intent.getParcelableExtra(EXTRA_CHILD_MODEL);
        if (mChildModel != null) {
            mIsEditMode = true;
        } else {
            mChildModel = new ChildModel();
            mChildModel.setClientId(mClientModel.getId());
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
        if (isChildValid()) {
            addSubscription(mChildrenInteractor.addChild(mChildModel)
                    .doOnSubscribe(() -> mChildrenView.showProgress())
                    .doOnUnsubscribe(() -> mChildrenView.hideProgress())
                    .subscribe(this::handleChildAddSuccess, this::handleChildRequestError));
        }
    }

    @Override
    public void editChild() {
        mChildModel.setName(mChildrenView.getChildName());
        if (isChildValid()) {
            addSubscription(mChildrenInteractor.editChild(mChildModel)
                    .doOnSubscribe((action) ->  mChildrenView.showProgress())
                    .doOnUnsubscribe(() -> mChildrenView.hideProgress())
                    .subscribe(this::handleChildEditSuccess, this::handleChildRequestError));
        }
    }

    @Override
    public void searchChildren() {
        addSubscription(mChildrenInteractor.searchChildren(mSearchChildrenView.getQuery())
                .doOnSubscribe(() -> mSearchChildrenView.showProgress())
                .doOnUnsubscribe(() -> mSearchChildrenView.hideProgress())
                .subscribe(this::handleChildrenFound, this::handleChildrenSearchError));
    }

    @Override
    public void searchClientChildren() {
        if (mClientModel.getChildren() != null && !mClientModel.getChildren().isEmpty()) {
            addSubscription(mChildrenInteractor.getChild(mClientModel)
                    .doOnSubscribe(() -> mChildrenView.showProgress())
                    .doOnUnsubscribe(() -> mChildrenView.hideProgress())
                    .subscribe(this::handleClientChildrenFound, this::handleClientChildrenSearchError));
        }
    }

    @Override
    public String getChildName() {
        return mChildModel != null ? mChildModel.getName() != null ? mChildModel.getName() : "" : "";
    }

    @Override
    public String getChildAge() {
        return mChildModel != null ? mChildModel.getBirthday() != null ? mChildModel.getBirthdayDateString() : "" : "";
    }

    @Override
    public String getChildGender() {
        return mChildModel != null ? mChildModel.getGender() : null;
    }

    @Override
    public void setChildBirthday(Date birthday) {
        mChildModel.setBirthday(birthday);
        mChildrenView.updateAge();
    }

    @Override
    public void setChildGender(String gender) {
        mChildModel.setGender(gender);
        mChildrenView.updateGender();
    }

    @Override
    public boolean isGenderSet() {
        return mChildModel.isFemale() || mChildModel.isMale();
    }

    @Override
    public void saveState(Bundle outState) {

    }

    @Override
    public void restoreState(Bundle savedInstanceState) {

    }

    private void handleChildAddSuccess(ChildModel childModel) {
        mChildModel.setId(childModel.getId());
        mChildrenView.showSuccessWithCloseAfter(R.string.child_added);
    }

    private void handleChildEditSuccess() {
        mChildrenView.showSuccessWithCloseAfter(R.string.child_edited);
    }

    private void handleChildRequestError(Throwable throwable) {
        mChildrenView.showError(throwable.getMessage());
    }

    private void handleChildrenFound(List<ChildModel> childrenList) {
        mSearchChildrenView.showChildsFound(childrenList);
    }

    private void handleChildrenSearchError(Throwable throwable) {
        mSearchChildrenView.showError(throwable.getMessage());
    }

    private void handleClientChildrenFound(List<ChildModel> childrenList) {
        mChildrenView.showPossibleChildren(childrenList);
    }

    private void handleClientChildrenSearchError(Throwable throwable) {
        //ignore
        throwable.printStackTrace();
    }

    private boolean isChildValid() {
        if (!mChildModel.isNameValid()) {
            mChildrenView.showError(R.string.child_name_not_set);
            return false;
        }

        if (!mChildModel.isAgeValid()) {
            mChildrenView.showError(R.string.child_age_not_set);
            return false;
        }

        if (!mChildModel.isGenderValid()) {
            mChildrenView.showError(R.string.child_gender_not_set);
            return false;
        }


        return true;
    }
}
