package com.verbatoria.infrastructure.retrofit.endpoints.submit.model.params

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class BCIDataItemParamsDto(
    @SerializedName("guid")
    var guid: String = "",
    @SerializedName("event_id")
    var sessionId: String = "",
    @SerializedName("action_id")
    var activityCode: Int = 0,
    @SerializedName("reserve_blank2")
    val questionnaire: String = "",
    @SerializedName("mistake")
    val applicationVersion: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    var attention: Int = 0,
    var mediation: Int= 0,
    var delta: Int = 0,
    var theta: Int = 0,
    @SerializedName("low_alpha")
    var lowAlpha: Int = 0,
    @SerializedName("high_alpha")
    var highAlpha: Int = 0,
    @SerializedName("low_beta")
    var lowBeta: Int = 0,
    @SerializedName("high_beta")
    var highBeta: Int = 0,
    @SerializedName("low_gamma")
    var lowGamma: Int = 0,
    @SerializedName("mid_gamma")
    var middleGamma: Int = 0
)