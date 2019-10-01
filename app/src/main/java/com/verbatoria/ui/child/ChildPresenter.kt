package com.verbatoria.ui.child

import com.verbatoria.domain.child.model.Child
import com.verbatoria.business.child.ChildInteractor
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class ChildPresenter(
    private val eventDetailMode: EventDetailMode,
    private var child: Child,
    private val clientId: String,
    private val childInteractor: ChildInteractor
) : BasePresenter<ChildView>(), ChildView.Callback {

    private var editedChild: Child = child.copy()

    override fun onAttachView(view: ChildView) {
        super.onAttachView(view)
        when {
            eventDetailMode.isCreateNew() -> {
                view.setEditableMode()
                checkIsAllFieldsFilled()
            }
            eventDetailMode.isStart() -> {
                view.setEditableMode()
                checkIsSomeFieldsChanged()
            }
            else -> view.setViewOnlyMode()
        }
        view.apply {
            setChildName(child.name)
            setChildGender(child.gender)
            setChildAge(child.age)
        }
    }

    //region ChildView.Callback

    override fun onChildNameChanged(newChildName: String) {
        if (eventDetailMode.isStart()) {
            editedChild.name = newChildName
            checkIsSomeFieldsChanged()
        } else if (eventDetailMode.isCreateNew()) {
            child.name = newChildName
            checkIsAllFieldsFilled()
        }
    }

    override fun onChildGenderSelected(newChildGender: Int) {
        if (eventDetailMode.isStart()) {
            editedChild.gender = newChildGender
            checkIsSomeFieldsChanged()
        } else if (eventDetailMode.isCreateNew()) {
            child.gender = newChildGender
            checkIsAllFieldsFilled()
        }
    }

    override fun onChildAgeSelected(newChildAge: Int) {
        if (eventDetailMode.isStart()) {
            editedChild.age = newChildAge
            view?.setChildAge(editedChild.age)
            checkIsSomeFieldsChanged()
        } else if (eventDetailMode.isCreateNew()) {
            child.age = newChildAge
            view?.setChildAge(child.age)
            checkIsAllFieldsFilled()
        }
    }

    override fun onSaveButtonClicked() {
        if (child.hasId()) {
            editChild()
        } else {
            createNewChild()
        }
    }

    override fun onChildAgeClicked() {
        if (eventDetailMode.isViewOnly()) {
            return
        }
        view?.showAgeSelectionDialog(false)
    }

    override fun onBackPressed() {
        view?.close()
    }

    override fun onNavigationClicked() {
        view?.close()
    }

    //endregion

    private fun checkIsAllFieldsFilled() {
        if (child.hasName() && child.hasAge() && child.hasGender()) {
            view?.setSaveButtonEnabled()
        } else {
            view?.setSaveButtonDisabled()
        }
    }

    private fun checkIsSomeFieldsChanged() {
        if (editedChild.hasName() && editedChild.hasGender() && editedChild.hasAge()
            && (child.name != editedChild.name || child.gender != editedChild.gender || child.age != editedChild.age)) {
            view?.setSaveButtonEnabled()
        } else {
            view?.setSaveButtonDisabled()
        }
    }

    private fun createNewChild() {
        view?.showSaveProgress()
        childInteractor.createNewChild(clientId, child)
            .doAfterTerminate {
                view?.hideSaveProgress()
            }
            .subscribe({ childId ->
                child.id = childId
                view?.close(child)
            }, { error ->
                error.printStackTrace()
                view?.showErrorSnackbar(error.localizedMessage)
            })
            .let(::addDisposable)
    }

    private fun editChild() {
        view?.showSaveProgress()
        childInteractor.editChild(clientId, editedChild)
            .doAfterTerminate {
                view?.hideSaveProgress()
            }
            .subscribe({
                view?.close(editedChild)
            }, { error ->
                error.printStackTrace()
                view?.showErrorSnackbar(error.localizedMessage)
            })
            .let(::addDisposable)
    }

}