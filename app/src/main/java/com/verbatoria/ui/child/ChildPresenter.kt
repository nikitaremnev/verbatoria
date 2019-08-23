package com.verbatoria.ui.child

import com.verbatoria.business.child.Child
import com.verbatoria.business.child.ChildInteractor
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.event.EventDetailMode
import java.util.*

/**
 * @author n.remnev
 */

class ChildPresenter(
    private val eventDetailMode: EventDetailMode,
    private var child: Child,
    private val clientId: String,
    private val childInteractor: ChildInteractor
) : BasePresenter<ChildView>(), ChildView.Callback {

    init {

    }

    override fun onAttachView(view: ChildView) {
        super.onAttachView(view)
        when {
            eventDetailMode.isCreateNew() -> view.setEditableMode()
            eventDetailMode.isEdit() -> view.setEditableMode()
            else -> view.setViewOnlyMode()
        }
    }

    //region ChildView.Callback

    override fun onChildNameChanged(newChildName: String) {
        child.name = newChildName
        checkIsAllFieldsFilled()
    }

    override fun onChildAgeSelected(age: Int) {

    }

    override fun onChildBirthdaySelected(birthday: Date) {

    }

    override fun onSaveButtonClicked() {

    }

    override fun onBackPressed() {
        view?.close()
    }

    override fun onNavigationClicked() {
        view?.close()
    }

    //endregion

    private fun checkIsAllFieldsFilled() {
        if (child.hasName()) {
            view?.setSaveButtonEnabled()
        } else {
            view?.setSaveButtonDisabled()
        }
    }


}