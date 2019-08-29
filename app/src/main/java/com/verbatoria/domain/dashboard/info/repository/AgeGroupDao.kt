package com.verbatoria.domain.dashboard.info.repository

import android.arch.persistence.room.*
import com.verbatoria.domain.dashboard.info.model.AgeGroupEntity

/**
 * @author n.remnev
 */

@Dao
interface AgeGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: Iterable<AgeGroupEntity>)

    @Query("DELETE FROM AgeGroupEntity")
    fun deleteAll()

    @Query("SELECT isArhimedesAllowed FROM AgeGroupEntity WHERE minAge > :age AND :age < maxAge")
    fun isAgeAvailableForArchimedes(age: Int): Boolean

}