package com.verbatoria.presentation.calendar.presenter.add.children;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.remnev.verbatoria.R;
import com.verbatoria.business.children.IChildrenInteractor;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.view.add.children.IChildView;
import com.verbatoria.presentation.calendar.view.add.children.search.ISearchChildrenView;
import java.util.Date;
import static com.verbatoria.presentation.calendar.view.add.children.ChildActivity.EXTRA_CHILD_MODEL;
import static com.verbatoria.presentation.calendar.view.add.children.ChildActivity.EXTRA_CLIENT_MODEL;

/**
 * Реализация презентера для экрана данных о детях
 *
 * @author nikitaremnev
 */
public class ChildPresenter extends BasePresenter implements IChildPresenter {

    private IChildrenInteractor childrenInteractor;

    private IChildView childView;
    private ISearchChildrenView searchChildView;

    private ChildModel childModel;
    private ClientModel clientModel;

    private boolean isEditMode;
    private boolean isSchoolAccount;

    public ChildPresenter(IChildrenInteractor childrenInteractor,
                          boolean isSchoolAccount) {
        this.childrenInteractor = childrenInteractor;
        this.isSchoolAccount = isSchoolAccount;
    }

    @Override
    public void bindView(@NonNull ISearchChildrenView searchChildrenView) {
        searchChildView = searchChildrenView;
    }

    @Override
    public void bindView(@NonNull IChildView childrenView) {
        childView = childrenView;
    }

    @Override
    public void unbindView() {
        searchChildView = null;
        childView = null;
    }

    @Override
    public void obtainChild(Intent intent) {
        clientModel = intent.getParcelableExtra(EXTRA_CLIENT_MODEL);
        childModel = intent.getParcelableExtra(EXTRA_CHILD_MODEL);
        if (childModel != null) {
            isEditMode = true;
        } else {
            childModel = new ChildModel();
            childModel.setClientId(clientModel.getId());
        }
    }

    @Override
    public boolean isEditMode() {
        return isEditMode;
    }

    @Override
    public void createChild() {
        childModel.setName(childView.getChildName());
        if (isChildValid()) {
            addSubscription(childrenInteractor.addChild(childModel)
                    .doOnSubscribe(() -> childView.showProgress())
                    .doOnUnsubscribe(() -> childView.hideProgress())
                    .subscribe(
                            (childModel) -> {
                                this.childModel.setId(childModel.getId());
                                childView.showSuccessWithCloseAfter(R.string.child_added);
                            },
                            (throwable) -> childView.showError(throwable.getMessage())
                    )
            );
        }
    }

    @Override
    public void editChild() {
        childModel.setName(childView.getChildName());
        if (isChildValid()) {
            addSubscription(childrenInteractor.editChild(childModel)
                    .doOnSubscribe((action) ->  childView.showProgress())
                    .doOnUnsubscribe(() -> childView.hideProgress())
                    .subscribe(
                            () -> childView.showSuccessWithCloseAfter(R.string.child_edited),
                            (throwable) -> childView.showError(throwable.getMessage())
                    )
            );
        }
    }

    @Override
    public void searchChildren() {
        addSubscription(childrenInteractor.searchChildren(searchChildView.getQuery())
                .doOnSubscribe(() -> searchChildView.showProgress())
                .doOnUnsubscribe(() -> searchChildView.hideProgress())
                .subscribe(
                        (childrenList) -> searchChildView.showChildsFound(childrenList),
                        (throwable) -> searchChildView.showError(throwable.getMessage())
                )
        );
    }

    @Override
    public void searchClientChildren() {
        if (clientModel.getChildren() != null && !clientModel.getChildren().isEmpty()) {
            addSubscription(childrenInteractor.getChild(clientModel)
                    .doOnSubscribe(() -> childView.showProgress())
                    .doOnUnsubscribe(() -> childView.hideProgress())
                    .subscribe(
                            (childrenList) -> childView.showPossibleChildren(childrenList),
                            Throwable::printStackTrace
                    )
            );
        }
    }

    @Override
    public String getChildName() {
        return childModel != null ? childModel.getName() != null ? childModel.getName() : "" : "";
    }

    @Override
    public String getChildAge() {
        return childModel != null ? childModel.getBirthday() != null ? childModel.getBirthdayDateString() : "" : "";
    }

    @Override
    public String getChildGender() {
        return childModel != null ? childModel.getGender() : null;
    }

    @Override
    public void setChildBirthday(Date birthday) {
        childModel.setBirthday(birthday);
        childView.updateAge();
    }

    @Override
    public void setChildGender(String gender) {
        childModel.setGender(gender);
        childView.updateGender();
    }

    @Override
    public boolean isGenderSet() {
        return childModel.isFemale() || childModel.isMale();
    }

    @Override
    public void onSuccessMessageDismissed() {
        childView.finishWithResult(childModel);
    }

    @Override
    public void onActivityResultChildFound() {
        childView.finishWithResult(childModel);
    }

    @Override
    public void onAgeFieldClicked() {
        childView.startAgePicker(isSchoolAccount);
    }

    @Override
    public void onBackPressed() {
        childView.finishWithResult(childModel);
    }

    @Override
    public void saveState(Bundle outState) {
        //empty
    }

    @Override
    public void restoreState(Bundle savedInstanceState) {
        //empty
    }

    private boolean isChildValid() {
        if (!childModel.isNameValid()) {
            childView.showError(R.string.child_name_not_set);
            return false;
        }

        if (!childModel.isAgeValid()) {
            childView.showError(R.string.child_age_not_set);
            return false;
        }

        if (!childModel.isGenderValid()) {
            childView.showError(R.string.child_gender_not_set);
            return false;
        }

        return true;
    }

}
