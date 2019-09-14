package com.verbatoria.domain.bci_data.model

/**
 * @author n.remnev
 */

private const val NO_DATA = -1
private const val EMPTY_DATA = 0

data class BCIData(
    var sessionId: String = "",
    val timestamp: Long,
    var activityCode: Int = NO_DATA,
    var attention: Int = NO_DATA,
    var mediation: Int = NO_DATA,
    var delta: Int = EMPTY_DATA,
    var theta: Int = EMPTY_DATA,
    var lowAlpha: Int = EMPTY_DATA,
    var highAlpha: Int = EMPTY_DATA,
    var lowBeta: Int = EMPTY_DATA,
    var highBeta: Int = EMPTY_DATA,
    var lowGamma: Int = EMPTY_DATA,
    var middleGamma: Int = EMPTY_DATA
) {

    fun isFilled(): Boolean =
        attention != NO_DATA && mediation != NO_DATA && delta != EMPTY_DATA && theta != EMPTY_DATA
                && lowAlpha != EMPTY_DATA && highAlpha != EMPTY_DATA && lowBeta != EMPTY_DATA &&
                highBeta != EMPTY_DATA && lowGamma != EMPTY_DATA && middleGamma != EMPTY_DATA

    fun isUnderActivity(): Boolean =
        activityCode != NO_DATA

}