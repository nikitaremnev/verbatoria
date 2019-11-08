package com.verbatoria.ui.dashboard.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.VerbatoriaKtApplication
import com.verbatoria.di.dashboard.DashboardComponent
import com.verbatoria.di.dashboard.info.InfoComponent
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterFragment
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.dialog.ProgressDialog
import com.verbatoria.utils.LocaleHelper

/**
 * @author n.remnev
 */

private const val PROGRESS_DIALOG_TAG = "PROGRESS_DIALOG_TAG"

interface InfoView : BaseView {

    fun setActiveStatus()

    fun setWarningStatus()

    fun setBlockedStatus()

    fun setName(name: String)

    fun setPhone(phone: String)

    fun setEmailPhone(email: String)

    fun setIsArchimedesAllowed(isArchimedesAllowed: Boolean)

    fun setLocationId(locationId: String)

    fun setLocationName(locationName: String)

    fun setLocationAddress(locationAddress: String)

    fun setLocationPoint(locationPoint: String)

    fun setLocale(locale: String)

    fun setPartnerName(partnerName: String)

    fun showInfoLoadingProgress()

    fun showLocationInfoLoadingProgress()

    fun showPartnerInfoLoadingProgress()

    fun hideInfoLoadingProgress()

    fun hideLocationInfoLoadingProgress()

    fun hidePartnerInfoLoadingProgress()

}

class InfoFragment :
    BasePresenterFragment<InfoView, DashboardComponent, InfoFragment, InfoComponent, InfoPresenter>(),
    InfoView {

    companion object {

        fun createFragment(): InfoFragment = InfoFragment()

    }

    private lateinit var statusView: View
    private lateinit var nameTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var archimedesTextView: TextView
    private lateinit var locationIdTextView: TextView
    private lateinit var locationNameTextView: TextView
    private lateinit var locationAddressTextView: TextView
    private lateinit var locationPointTextView: TextView
    private lateinit var partnerNameTextView: TextView
    private lateinit var infoProgressBar: ProgressBar
    private lateinit var locationInfoProgressBar: ProgressBar
    private lateinit var partnerInfoProgressBar: ProgressBar

    override fun buildComponent(
        parentComponent: DashboardComponent,
        savedState: Bundle?
    ): InfoComponent =
        parentComponent.plusInfoComponent()
            .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_info, container, false)
        rootView.apply {
            statusView = findViewById(R.id.status_view)
            nameTextView = findViewById(R.id.name_text_view)
            phoneTextView = findViewById(R.id.phone_text_view)
            emailTextView = findViewById(R.id.email_text_view)
            archimedesTextView = findViewById(R.id.archimedes_text_view)
            locationIdTextView = findViewById(R.id.location_id_text_view)
            locationNameTextView = findViewById(R.id.location_name_text_view)
            locationAddressTextView = findViewById(R.id.location_address_text_view)
            locationPointTextView = findViewById(R.id.location_point_text_view)
            partnerNameTextView = findViewById(R.id.partner_name_text_view)
            infoProgressBar = findViewById(R.id.info_progress_bar)
            locationInfoProgressBar = findViewById(R.id.location_info_progress_bar)
            partnerInfoProgressBar = findViewById(R.id.partner_info_progress_bar)
        }
        return rootView
    }

    //region SettingsView

    override fun setActiveStatus() {
        statusView.background = context?.getDrawable(R.drawable.verbatolog_status_green)
    }

    override fun setWarningStatus() {
        statusView.background = context?.getDrawable(R.drawable.verbatolog_status_yellow)
    }

    override fun setBlockedStatus() {
        statusView.background = context?.getDrawable(R.drawable.verbatolog_status_red)
    }

    override fun setName(name: String) {
        nameTextView.text = name
    }

    override fun setPhone(phone: String) {
        phoneTextView.text = phone
    }

    override fun setEmailPhone(email: String) {
        emailTextView.text = email
    }

    override fun setIsArchimedesAllowed(isArchimedesAllowed: Boolean) {
        if (isArchimedesAllowed) {
            archimedesTextView.text = getString(R.string.dashboard_archimedes_available)
        } else {
            archimedesTextView.text = getString(R.string.dashboard_archimedes_not_available)
        }
    }

    override fun setLocationId(locationId: String) {
        locationIdTextView.text = locationId
    }

    override fun setLocationName(locationName: String) {
        locationNameTextView.text = locationName
    }

    override fun setLocationAddress(locationAddress: String) {
        locationAddressTextView.text = locationAddress
    }

    override fun setLocationPoint(locationPoint: String) {
        locationPointTextView.text = locationPoint
    }

    override fun setLocale(locale: String) {
        activity?.let { context ->
            LocaleHelper.setLocale(context, locale)
        }
        (activity?.applicationContext as? VerbatoriaKtApplication)?.updateCurrentLocale(locale)
        activity?.recreate()
    }

    override fun setPartnerName(partnerName: String) {
        partnerNameTextView.text = partnerName
    }

    override fun showInfoLoadingProgress() {
        infoProgressBar.show()
    }

    override fun showLocationInfoLoadingProgress() {
        locationInfoProgressBar.show()
    }

    override fun showPartnerInfoLoadingProgress() {
        partnerInfoProgressBar.show()
    }

    override fun hideInfoLoadingProgress() {
        infoProgressBar.hide()
    }

    override fun hideLocationInfoLoadingProgress() {
        locationInfoProgressBar.hide()
    }

    override fun hidePartnerInfoLoadingProgress() {
        partnerInfoProgressBar.hide()
    }

    //endregion

}