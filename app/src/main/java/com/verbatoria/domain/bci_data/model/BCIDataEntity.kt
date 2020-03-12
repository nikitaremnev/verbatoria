package com.verbatoria.domain.bci_data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author n.remnev
 */

@Entity(tableName = "BCIDataEntity")
class BCIDataEntity(
    @PrimaryKey
    val id: String,
    val sessionId: String,
    var connectedDeviceMacAddress: String,
    val timestamp: Long,
    val activityCode: Int,
    val attention: Int,
    val mediation: Int,
    var delta: Int,
    var theta: Int,
    var lowAlpha: Int,
    var highAlpha: Int,
    var lowBeta: Int,
    var highBeta: Int,
    var lowGamma: Int,
    var middleGamma: Int
)