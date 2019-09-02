package com.verbatoria.domain.activities.repository

import android.arch.persistence.room.*
import com.verbatoria.domain.activities.model.ActivityEntity

/**
 * @author n.remnev
 */

@Dao
interface ActivitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: ActivityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: Iterable<ActivityEntity>)

    @Query("DELETE FROM ActivityEntity WHERE eventId = :eventId")
    fun deleteByEventId(eventId: String)

    @Query("SELECT * FROM ActivityEntity WHERE eventId = :eventId")
    fun findByEventId(eventId: String): List<ActivityEntity>

}