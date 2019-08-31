package com.verbatoria.domain.activities.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author n.remnev
 */

@Entity(tableName = "ActivityEntity")
class ActivityEntity(
    @PrimaryKey
    val id: String,
    val eventId: String,
    val activityCode: Int,
    val startTime: Long,
    val endTime: Long
)