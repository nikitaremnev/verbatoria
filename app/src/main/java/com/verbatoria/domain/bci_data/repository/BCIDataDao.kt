package com.verbatoria.domain.bci_data.repository

import android.arch.persistence.room.*
import com.verbatoria.domain.bci_data.model.BCIDataEntity

/**
 * @author n.remnev
 */

@Dao
interface BCIDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: BCIDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: Iterable<BCIDataEntity>)

    @Query("DELETE FROM BCIDataEntity WHERE eventId = :eventId")
    fun deleteByEventId(eventId: String)

    @Query("SELECT * FROM BCIDataEntity WHERE eventId = :eventId")
    fun findByEventId(eventId: String): List<BCIDataEntity>

}