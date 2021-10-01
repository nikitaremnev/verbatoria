package com.verbatoria.ui.dashboard.settings

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.remnev.verbatoria.R
import com.verbatoria.VerbatoriaKtApplication
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
private const val PROGRESS_DIALOG_TAG = "PROGRESS_DIALOG_TAG"

interface SettingsView : BaseView {

    fun showAboutAppDialog(appVersion: String, androidVersion: String)

    fun showAppLanguagesDialog(
        isRussianLanguageAvailable: Boolean,
        isEnglishLanguageAvailable: Boolean,
        isHongKongLanguageAvailable: Boolean,
        isUkrainianLanguageAvailable: Boolean,
        isBulgarianLanguageAvailable: Boolean,
        isTurkeyLanguageAvailable: Boolean,
        isArabicLanguageAvailable: Boolean,
        isBosniaLanguageAvailable: Boolean,
        isGreeceLanguageAvailable: Boolean,
        isMongolianLanguageAvailable: Boolean,
        currentLocale: String
    )

    fun setSettingsItems(settingsItemModels: List<SettingsItemModel>)

    fun setLocale(locale: String)

    fun showProgress()

    fun hideProgress()

    fun openLogin()

    fun openSchedule()

    fun openLateSend()

    interface Callback {

        fun onRussianLanguageSelected()

        fun onEnglishLanguageSelected()

        fun onHongKongLanguageSelected()

        fun onUkrainianLanguageSelected()

        fun onBulgarianLanguageSelected()

        fun onTurkeyLanguageSelected()

        fun onArabicLanguageSelected()

        fun onBosnianLanguageSelected()

        fun onGreeceLanguageSelected()

        fun onMongolianLanguageSelected()

    }

}

class SettingsFragment :
    BasePresenterFragment<SettingsView, DashboardComponent, SettingsFragment, SettingsComponent, SettingsPresenter>(),
    SettingsView, AppLanguagesDialog.OnLanguageSelectedDialogListener {

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

    override fun showAppLanguagesDialog(
        isRussianLanguageAvailable: Boolean,
        isEnglishLanguageAvailable: Boolean,
        isHongKongLanguageAvailable: Boolean,
        isUkrainianLanguageAvailable: Boolean,
        isBulgarianLanguageAvailable: Boolean,
        isTurkeyLanguageAvailable: Boolean,
        isArabicLanguageAvailable: Boolean,
        isBosniaLanguageAvailable: Boolean,
        isGreeceLanguageAvailable: Boolean,
        isMongolianLanguageAvailable: Boolean,
        currentLocale: String
    ) {
        AppLanguagesDialog.build {
            this.isRussianLanguageAvailable = isRussianLanguageAvailable
            this.isEnglishLanguageAvailable = isEnglishLanguageAvailable
            this.isHongKongLanguageAvailable = isHongKongLanguageAvailable
            this.isUkrainianLanguageAvailable = isUkrainianLanguageAvailable
            this.isBulgarianLanguageAvailable = isBulgarianLanguageAvailable
            this.isTurkeyLanguageAvailable = isTurkeyLanguageAvailable
            this.isArabicLanguageAvailable = isArabicLanguageAvailable
            this.isBosniaLanguageAvailable = isBosniaLanguageAvailable
            this.isGreeceLanguageAvailable = isGreeceLanguageAvailable
            this.isMongolianLanguageAvailable = isMongolianLanguageAvailable
            this.currentLocale = currentLocale
        }.show(activity?.supportFragmentManager, APP_LANGUAGES_DIALOG_TAG)
    }

    override fun setSettingsItems(settingsItemModels: List<SettingsItemModel>) {
        adapter.update(settingsItemModels)
    }

    override fun setLocale(locale: String) {
        activity?.let { context ->
            LocaleHelper.setLocale(context, locale)
        }
        (activity?.applicationContext as? VerbatoriaKtApplication)?.updateCurrentLocale(locale)
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

    override fun onBulgarianLanguageSelected() {
        presenter.onBulgarianLanguageSelected()
    }

    override fun onTurkeyLanguageSelected() {
        presenter.onTurkeyLanguageSelected()
    }

    override fun onArabicLanguageSelected() {
        presenter.onArabicLanguageSelected()
    }

    override fun onBosnianLanguageSelected() {
        presenter.onBosnianLanguageSelected()
    }

    override fun onGreeceLanguageSelected() {
        presenter.onGreeceLanguageSelected()
    }

    override fun onMongolianLanguageSelected() {
        presenter.onMongolianLanguageSelected()
    }

    //endregion

}
