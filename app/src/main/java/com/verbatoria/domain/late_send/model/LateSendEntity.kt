package com.verbatoria.domain.late_send.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * @author n.remnev
 */

@Entity(tableName = "LateSendEntity")
class LateSendEntity(
    @PrimaryKey
    val sessionId: String,
    val eventId: String,
    val childName: String,
    val childAge: Int,
    val startDate: Date,
    val endDate: Date,
    val state: Int
)