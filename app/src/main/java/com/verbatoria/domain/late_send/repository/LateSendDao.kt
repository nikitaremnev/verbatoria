package com.verbatoria.domain.late_send.repository

import android.arch.persistence.room.*
import com.verbatoria.domain.late_send.model.LateSendEntity

/**
 * @author n.remnev
 */

@Dao
interface LateSendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: LateSendEntity)

    @Query("DELETE FROM LateSendEntity WHERE sessionId = :sessionId")
    fun deleteBySessionId(sessionId: String)

    @Query("SELECT * FROM LateSendEntity WHERE sessionId = :sessionId LIMIT 1")
    fun findBySessionId(sessionId: String): LateSendEntity?

    @Query("UPDATE LateSendEntity SET state = :state WHERE sessionId = :sessionId")
    fun updateState(sessionId: String, state: Int)

    @Query("SELECT * FROM LateSendEntity")
    fun findAll(): List<LateSendEntity>

}