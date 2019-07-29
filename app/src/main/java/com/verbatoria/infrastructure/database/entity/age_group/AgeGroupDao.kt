package com.verbatoria.infrastructure.database.entity.age_group

import android.arch.persistence.room.*

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