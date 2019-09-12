package com.verbatoria.domain.bci_data.model

/**
 * @author n.remnev
 */

private const val NO_DATA = -1

data class BCIData(
    var eventId: String = "",
    val timestamp: Long,
    val activityCode: Int,
    var attention: Int = NO_DATA,
    var mediation: Int = NO_DATA,
    var delta: Int = 0,
    var theta: Int = 0,
    var lowAlpha: Int = 0,
    var highAlpha: Int = 0,
    var lowBeta: Int = 0,
    var highBeta: Int = 0,
    var lowGamma: Int = 0,
    var middleGamma: Int = 0
) {

    fun isFilled(): Boolean =
        attention != NO_DATA && mediation != NO_DATA && delta != 0 && theta != 0 && lowAlpha != 0 && highAlpha != 0 && lowBeta != 0 && highBeta != 0 && lowGamma != 0 && middleGamma != 0

}