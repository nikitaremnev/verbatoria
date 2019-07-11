package com.verbatoria.infrastructure.database.entity

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