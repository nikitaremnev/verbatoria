package com.verbatoria.domain.bci_data.model

/**
 * @author n.remnev
 */

data class BCIData(
    val id: String,
    val eventId: String,
    val timestamp: Long,
    var attention: Int = 0,
    var mediation: Int = 0,
    var delta: Int = 0,
    var theta: Int = 0,
    var lowAlpha: Int = 0,
    var highAlpha: Int = 0,
    var lowBeta: Int = 0,
    var highBeta: Int = 0,
    var lowGamma: Int = 0,
    var middleGamma: Int = 0
)