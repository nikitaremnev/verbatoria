package com.verbatoria.domain.late_send

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author n.remnev
 */

@Entity(tableName = "LateSendEntity")
class LateSendEntity(
    @PrimaryKey
    val id: String,
    val shit: String = ""
)