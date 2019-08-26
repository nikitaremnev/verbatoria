package com.verbatoria.ui.dashboard.settings

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.settings.model.item.SettingsItemModel
import com.verbatoria.di.dashboard.DashboardComponent
import com.verbatoria.di.dashboard.settings.SettingsComponent
import com.verbatoria.ui.base.BasePresenterFragment
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.dialog.ProgressDialog
import com.verbatoria.ui.common.dialog.FragmentSuggestDialog
import com.verbatoria.ui.late_send.LateSendActivity
import com.verbatoria.ui.login.LoginActivity
import com.verbatoria.ui.schedule.ScheduleActivity
import com.verbatoria.utils.LocaleHelper
import javax.inject.Inject

/**
 * @author n.remnev
 */

private const val APP_LANGUAGES_DIALOG_TAG = "APP_LANGUAGES_DIALOG_TAG"
private const val ABOUT_APP_DIALOG_TAG = "ABOUT_APP_DIALOG_TAG"
private const val CLEAR_DATABASE_CONFIRMATION_DIALOG_TAG = "CLEAR_DATABASE_CONFIRMATION_DIALOG_TAG"
private const val PROGRESS_DIALOG_TAG = "PROGRESS_DIALOG_TAG"

interface SettingsView : BaseView {

    fun showAboutAppDialog(appVersion: String, androidVersion: String)

    fun showClearDatabaseConfirmationDialog()

    fun showAppLanguagesDialog(
        isRussianLanguageAvailable: Boolean,
        isEnglishLanguageAvailable: Boolean,
        isHongKongLanguageAvailable: Boolean,
        isUkrainianLanguageAvailable: Boolean
    )

    fun setSettingsItems(settingsItemModels: List<SettingsItemModel>)

    fun setLanguage(locale: String)

    fun showProgress()

    fun hideProgress()

    fun openLogin()

    fun openSchedule()

    fun openLateSend()

    interface Callback {

        fun onDatabaseClearConfirmed()

        fun onRussianLanguageSelected()

        fun onEnglishLanguageSelected()

        fun onHongKongLanguageSelected()

        fun onUkrainianLanguageSelected()

    }

}

class SettingsFragment :
    BasePresenterFragment<SettingsView, DashboardComponent, SettingsFragment, SettingsComponent, SettingsPresenter>(),
    SettingsView, FragmentSuggestDialog.OnClickSuggestDialogListener, AppLanguagesDialog.OnLanguageSelectedDialogListener {

    companion object {

        fun createFragment(): SettingsFragment = SettingsFragment()

    }

    @Inject
    lateinit var adapter: Adapter

    private lateinit var recyclerView: RecyclerView

    private var progressDialog: ProgressDialog? = null

    override fun buildComponent(
        parentComponent: DashboardComponent,
        savedState: Bundle?
    ): SettingsComponent =
        parentComponent.plusSettingsComponent()
            .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        recyclerView = rootView as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        return rootView

    }

    //region SettingsView

    override fun showAboutAppDialog(appVersion: String, androidVersion: String) {
        AboutAppDialog.build {
            this.appVersion = appVersion
            this.androidVersion = androidVersion
        }
            .show(activity?.supportFragmentManager, ABOUT_APP_DIALOG_TAG)
    }

    override fun showClearDatabaseConfirmationDialog() {
        FragmentSuggestDialog.build {
            title = getString(R.string.settings_clear_database_confirm_title)
            message = getString(R.string.settings_clear_database_confirm_message)
            positiveTitleBtn = getString(R.string.settings_item_clear)
            negativeTitleBtn = getString(R.string.cancel)
        }.show(activity?.supportFragmentManager, CLEAR_DATABASE_CONFIRMATION_DIALOG_TAG)
    }

    override fun showAppLanguagesDialog(
        isRussianLanguageAvailable: Boolean,
        isEnglishLanguageAvailable: Boolean,
        isHongKongLanguageAvailable: Boolean,
        isUkrainianLanguageAvailable: Boolean
    ) {
        AppLanguagesDialog.build {
            this.isRussianLanguageAvailable = isRussianLanguageAvailable
            this.isEnglishLanguageAvailable = isEnglishLanguageAvailable
            this.isHongKongLanguageAvailable = isHongKongLanguageAvailable
            this.isUkrainianLanguageAvailable = isUkrainianLanguageAvailable
        }.show(activity?.supportFragmentManager, APP_LANGUAGES_DIALOG_TAG)
    }

    override fun setSettingsItems(settingsItemModels: List<SettingsItemModel>) {
        adapter.update(settingsItemModels)
    }

    override fun setLanguage(locale: String) {
        LocaleHelper.setLocale(activity, locale)
        activity?.recreate()
    }

    override fun showProgress() {
        progressDialog = ProgressDialog()
        progressDialog?.show(activity?.supportFragmentManager, PROGRESS_DIALOG_TAG)
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun openLogin() {
        activity?.let { activity ->
            startActivity(LoginActivity.createIntentWithClearStack(activity))
        }
    }

    override fun openSchedule() {
        activity?.let { activity ->
            startActivity(ScheduleActivity.createIntent(activity))
        }
    }

    override fun openLateSend() {
        activity?.let { activity ->
            startActivity(LateSendActivity.createIntent(activity))
        }
    }

    //endregion

    //region FragmentSuggestDialog.OnClickSuggestDialogListener

    override fun onPositiveClicked(tag: String?) {
        if (tag == CLEAR_DATABASE_CONFIRMATION_DIALOG_TAG) {
            presenter.onDatabaseClearConfirmed()
        }
    }

    override fun onNegativeClicked(tag: String?) {
        //empty
    }

    //endregion

    //region AppLanguagesDialog.OnLanguageSelectedDialogListener

    override fun onRussianLanguageSelected() {
        presenter.onRussianLanguageSelected()
    }

    override fun onEnglishLanguageSelected() {
        presenter.onEnglishLanguageSelected()
    }

    override fun onHongKongLanguageSelected() {
        presenter.onHongKongLanguageSelected()
    }

    override fun onUkrainianLanguageSelected() {
        presenter.onUkrainianLanguageSelected()
    }

    //endregion

}