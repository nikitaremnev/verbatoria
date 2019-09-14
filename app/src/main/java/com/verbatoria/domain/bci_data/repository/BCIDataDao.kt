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

    @Query("DELETE FROM BCIDataEntity WHERE sessionId = :sessionId")
    fun deleteBySessionId(sessionId: String)

    @Query("SELECT * FROM BCIDataEntity WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    fun findAllBySessionId(sessionId: String): List<BCIDataEntity>

}