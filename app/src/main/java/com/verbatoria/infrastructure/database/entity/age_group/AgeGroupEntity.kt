package com.verbatoria.infrastructure.database.entity.age_group

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