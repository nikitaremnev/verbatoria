package com.verbatoria.domain.dashboard.info.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * @author n.remnev
 */

@Entity(tableName = "AgeGroupEntity")
class AgeGroupEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val minAge: Int,
    val maxAge: Int,
    val isArhimedesAllowed: Boolean
)