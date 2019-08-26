package com.verbatoria.ui.child

import com.verbatoria.domain.child.Child
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

    init {

    }

    override fun onAttachView(view: ChildView) {
        super.onAttachView(view)
        when {
            eventDetailMode.isCreateNew() -> view.setEditableMode()
            eventDetailMode.isEdit() -> view.setEditableMode()
            else -> view.setViewOnlyMode()
        }
        view.apply {
            setChildName(child.name)
            setChildGender(child.gender)
            setChildAge(child.age)
        }
        checkIsAllFieldsFilled()
    }

    //region ChildView.Callback

    override fun onChildNameChanged(newChildName: String) {
        child.name = newChildName
        checkIsAllFieldsFilled()
    }

    override fun onChildGenderSelected(newChildGender: Int) {
        if (eventDetailMode.isStart()) {
            editedChild.gender = newChildGender
            checkIsSomeFieldsChanged()
        } else if (eventDetailMode.isCreateNew()) {
            child.gender = newChildGender
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
        if (child.hasName() && child.hasAge()) {
            view?.setSaveButtonEnabled()
        } else {
            view?.setSaveButtonDisabled()
        }
    }

    private fun checkIsSomeFieldsChanged() {
        if (child.gender != editedChild.gender || child.age != editedChild.age) {
            view?.showSaveButton()
        } else {
            view?.hideSaveButton()
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