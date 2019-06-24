package com.verbatoria.presentation.base

/**
 * @author n.remnev
 */

interface BaseView {

    fun showSnackbar(text: String)

    fun showHintSnackbar(hintString: String)

    fun showShortHintSnackbar(shortHintString: String)

    fun showWarningSnackbar(warningString: String)

    fun showErrorSnackbar(errorString: String)

}