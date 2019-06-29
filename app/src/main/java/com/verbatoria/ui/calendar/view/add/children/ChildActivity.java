package com.verbatoria.ui.calendar.view.add.children;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.di.calendar.CalendarModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.ui.calendar.presenter.add.children.IChildPresenter;
import com.verbatoria.ui.calendar.view.add.children.age.ChildAgeClickListener;
import com.verbatoria.ui.calendar.view.add.children.age.ChildAgeDialogFragment;
import com.verbatoria.ui.calendar.view.add.children.search.SearchChildrenActivity;
import com.verbatoria.utils.Helper;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;

import static com.verbatoria.business.dashboard.models.ChildModel.FEMALE_GENDER;
import static com.verbatoria.business.dashboard.models.ChildModel.MALE_GENDER;
import static com.verbatoria.utils.LocaleHelper.LOCALE_RU;

/**
 * Экран добавления детей
 *
 * @author nikitaremnev
 */
public class ChildActivity extends BaseActivity implements IChildView, ChildAgeClickListener {

    private static final String TAG = "ChildActivity";
    public static final String EXTRA_CHILD_MODEL = "com.verbatoria.presentation.calendar.view.add.children.EXTRA_CHILD_MODEL";
    public static final String EXTRA_CLIENT_MODEL = "com.verbatoria.presentation.calendar.view.add.children.EXTRA_CLIENT_MODEL";

    public static final int ACTIVITY_SEARCH_CHILDREN_CODE = 12;

    @Inject
    IChildPresenter mChildrenPresenter;

    @BindView(R.id.child_name_field)
    public View mChildNameField;

    @BindView(R.id.child_birthday_field)
    public View mChildBirthdayField;

    @BindView(R.id.child_name_editable_field)
    public View mChildNameEditableField;

    @BindView(R.id.gender_radio_group)
    public RadioGroup mGenderRadioGroup;

    @BindView(R.id.male_radio_button)
    public RadioButton mMaleRadioButton;

    @BindView(R.id.female_radio_button)
    public RadioButton mFemaleRadioButton;

    @BindView(R.id.gender_status_image_view)
    public View mGenderErrorView;

    @BindView(R.id.child_gender_field)
    public View mGenderField;

    @BindView(R.id.submit_button)
    public Button mSubmitButton;

    private Calendar mBirthday;

    private MenuItem mEditMenuItem;
    private MenuItem mCancelMenuItem;

    public static Intent newInstance(Context mContext, ChildModel childModel, ClientModel clientModel) {
        Intent intent = new Intent(mContext, ChildActivity.class);
        intent.putExtra(EXTRA_CHILD_MODEL, childModel);
        intent.putExtra(EXTRA_CLIENT_MODEL, clientModel);
        return intent;
    }

    public static Intent newInstance(Context mContext, ClientModel clientModel) {
        Intent intent = new Intent(mContext, ChildActivity.class);
        intent.putExtra(EXTRA_CLIENT_MODEL, clientModel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getInjector().addModule(new CalendarModule()).inject(this);
        mChildrenPresenter.obtainChild(getIntent());

        setContentView(R.layout.activity_child);

        setPresenter((BasePresenter) mChildrenPresenter);
        mChildrenPresenter.bindView(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mChildrenPresenter.isEditMode()) {
            mChildrenPresenter.searchClientChildren();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_edit, menu);
        mCancelMenuItem = menu.findItem(R.id.action_cancel);
        mEditMenuItem = menu.findItem(R.id.action_edit);
        mEditMenuItem.setVisible(mChildrenPresenter.isEditMode());
        mCancelMenuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        mChildrenPresenter.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_SEARCH_CHILDREN_CODE && resultCode == RESULT_OK) {
            mChildrenPresenter.obtainChild(data);
            mChildrenPresenter.onActivityResultChildFound();
        }
    }

    @Override
    protected void setUpViews() {
        setUpNavigation();
        setUpFields();
        setUpEditableFields();
        if (mChildrenPresenter.isEditMode()) {
            setUpReadonlyMode();
        } else {
            setUpNewChildMode();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.action_edit) {
            setUpEditableMode();
            Helper.showHintSnackBar(mSubmitButton, getString(R.string.children_activity_edit_hint));
        }
        if (item.getItemId() == R.id.action_cancel) {
            setUpReadonlyMode();
            Helper.showHintSnackBar(mSubmitButton, getString(R.string.children_activity_cancel_edit_hint));
        }
        if (item.getItemId() == R.id.action_search) {
            startSearch();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        startProgress();
    }

    @Override
    public void hideProgress() {
        stopProgress();
    }

    @Override
    public void setUpEditableMode() {
        mChildNameEditableField.setVisibility(View.VISIBLE);
        mChildNameField.setVisibility(View.GONE);
        if (mEditMenuItem != null && mCancelMenuItem != null) {
            mEditMenuItem.setVisible(false);
            mCancelMenuItem.setVisible(true);
        }
        mGenderRadioGroup.setEnabled(true);
        mFemaleRadioButton.setEnabled(true);
        mMaleRadioButton.setEnabled(true);
        mGenderErrorView.setVisibility(View.GONE);
        mGenderField.setVisibility(View.VISIBLE);
        mSubmitButton.setText(getString(R.string.save));
        mSubmitButton.setVisibility(View.VISIBLE);
        mSubmitButton.setOnClickListener(v -> mChildrenPresenter.editChild());
        mMaleRadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mChildrenPresenter.setChildGender(MALE_GENDER);
            }
        });
        mFemaleRadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mChildrenPresenter.setChildGender(FEMALE_GENDER);
            }
        });
    }

    @Override
    public void setUpNewChildMode() {
        mChildNameEditableField.setVisibility(View.VISIBLE);
        mChildNameField.setVisibility(View.GONE);
        if (mEditMenuItem != null && mCancelMenuItem != null) {
            mEditMenuItem.setVisible(false);
            mCancelMenuItem.setVisible(true);
        }
        mGenderRadioGroup.setEnabled(true);
        mFemaleRadioButton.setEnabled(true);
        mMaleRadioButton.setEnabled(true);
        mGenderErrorView.setVisibility(View.GONE);
        mGenderField.setVisibility(View.VISIBLE);
        mSubmitButton.setText(getString(R.string.calendar_activity_create));
        mSubmitButton.setVisibility(View.VISIBLE);
        mSubmitButton.setOnClickListener(v -> mChildrenPresenter.createChild());
        mMaleRadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mChildrenPresenter.setChildGender(MALE_GENDER);
            }
        });
        mFemaleRadioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mChildrenPresenter.setChildGender(FEMALE_GENDER);
            }
        });
    }

    @Override
    public void setUpReadonlyMode() {
        mChildNameEditableField.setVisibility(View.GONE);
        mChildNameField.setVisibility(View.VISIBLE);
        mChildBirthdayField.setVisibility(View.VISIBLE);
        mGenderRadioGroup.setEnabled(false);
        mFemaleRadioButton.setEnabled(false);
        mMaleRadioButton.setEnabled(false);
        mGenderErrorView.setVisibility(!mChildrenPresenter.isGenderSet() ? View.VISIBLE : View.GONE);
        if (mEditMenuItem != null && mCancelMenuItem != null) {
            if (mChildrenPresenter.isEditMode()) {
                mEditMenuItem.setVisible(true);
                mCancelMenuItem.setVisible(false);
            } else {
                mEditMenuItem.setVisible(false);
                mCancelMenuItem.setVisible(false);
            }
        }
        mSubmitButton.setVisibility(View.GONE);
    }

    @Override
    public String getChildName() {
        return getFieldValue(mChildNameEditableField);
    }

    @Override
    public void finishWithResult(ChildModel childModel) {
        setResult(RESULT_OK, createChildIntent(childModel));
        finish();
    }

    public static Intent createChildIntent(ChildModel childModel) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CHILD_MODEL, childModel);
        return intent;
    }

    @Override
    public void startAgePicker(boolean isSchoolMode) {
        mBirthday = null;
        ChildAgeDialogFragment
                .newInstance(isSchoolMode)
                .show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void updateAge() {
        String childAgeString = TextUtils.isEmpty(mChildrenPresenter.getChildAge()) ? getString(R.string.event_detail_activity_field_empty): mChildrenPresenter.getChildAge();
        setUpFieldView(mChildBirthdayField, R.drawable.ic_child_birthday, childAgeString, getString(R.string.event_detail_activity_child_age), v -> {
            if (mSubmitButton.getVisibility() == View.VISIBLE) {
                mChildrenPresenter.onAgeFieldClicked();
            }
        });
    }

    @Override
    public void updateGender() {
        String gender = mChildrenPresenter.getChildGender();
        mGenderErrorView.setVisibility(View.VISIBLE);
        if (ChildModel.isFemale(gender)) {
            mFemaleRadioButton.setChecked(true);
            mGenderErrorView.setVisibility(View.GONE);
        }
        if (ChildModel.isMale(gender)) {
            mMaleRadioButton.setChecked(true);
            mGenderErrorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSuccessWithCloseAfter(int messageStringResource) {
        Snackbar snackbar = Snackbar.make(mSubmitButton, getString(messageStringResource), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.hint_color);
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                mChildrenPresenter.onSuccessMessageDismissed();
            }
        });
        snackbar.show();
    }

    @Override
    public void showPossibleChildren(List<ChildModel> children) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.calendar_possible_children));
        builder.setSingleChoiceItems(getChildrenNames(children), -1, (dialog, which) -> {
            setResult(RESULT_OK, createChildIntent(children.get(which)));
            finish();
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.create().show();
    }

    @Override
    public void onChildAgeClicked(int age) {
        mBirthday = Calendar.getInstance(new Locale(LOCALE_RU));
        mBirthday.set(Calendar.YEAR, mBirthday.get(Calendar.YEAR) - age);
        mBirthday.set(Calendar.DAY_OF_MONTH, mBirthday.get(Calendar.DAY_OF_MONTH) - 7);
        mChildrenPresenter.setChildBirthday(mBirthday.getTime());
    }

    private void setUpNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.calendar_activity_child_title));
    }

    private void setUpFields() {
        String childNameString = TextUtils.isEmpty(mChildrenPresenter.getChildName()) ? getString(R.string.event_detail_activity_field_empty): mChildrenPresenter.getChildName();
        setUpFieldView(mChildNameField, R.drawable.ic_child_name, childNameString, getString(R.string.event_detail_activity_child_name), v -> {});
        updateAge();
        updateGender();
    }

    private void setUpEditableFields() {
        setUpEditableFieldView(mChildNameEditableField, R.drawable.ic_child_name, mChildrenPresenter.getChildName(),
                getString(R.string.event_detail_activity_child_name_editable_hint), InputType.TYPE_TEXT_FLAG_CAP_WORDS);
    }

    private void setUpFieldView(View fieldView, int imageResource, String title, String subtitle, View.OnClickListener onClickListener) {
        ((ImageView) fieldView.findViewById(R.id.field_image_view)).setImageResource(imageResource);
        ((TextView) fieldView.findViewById(R.id.field_title)).setText(title);
        ((TextView) fieldView.findViewById(R.id.field_subtitle)).setText(subtitle);
        fieldView.findViewById(R.id.status_image_view).setVisibility(View.GONE);
        fieldView.setOnClickListener(onClickListener);
    }

    private void setUpEditableFieldView(View fieldView, int imageResource, String title, String hint, int inputType) {
        ((ImageView) fieldView.findViewById(R.id.field_image_view)).setImageResource(imageResource);
        fieldView.findViewById(R.id.status_image_view).setVisibility(View.GONE);
        EditText fieldEditText = fieldView.findViewById(R.id.field_edit_text);
        fieldEditText.setInputType(inputType);
        fieldEditText.setText(title);
        fieldEditText.setHint(hint);
    }

    private String getFieldValue(View fieldView) {
        EditText fieldEditText = fieldView.findViewById(R.id.field_edit_text);
        return fieldEditText.getText().toString();
    }

    private void startSearch() {
        startActivityForResult(SearchChildrenActivity.newInstance(this), ACTIVITY_SEARCH_CHILDREN_CODE);
    }

    private String[] getChildrenNames(List<ChildModel> children) {
        String[] childrenNames = new String[children.size()];
        int index = 0;
        for (ChildModel child : children) {
            childrenNames[index] = child.getName();
            index ++;
        }
        return childrenNames;
    }

    //region errors handling

    @Override
    public void showError(String message) {
        Helper.showErrorSnackBar(mSubmitButton, message);
    }

    @Override
    public void showError(int errorStringResource) {
        Helper.showErrorSnackBar(mSubmitButton, getString(errorStringResource));
    }

    //endregion
}
