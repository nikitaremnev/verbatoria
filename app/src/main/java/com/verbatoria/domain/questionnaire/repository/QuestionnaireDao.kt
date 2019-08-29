package com.verbatoria.domain.questionnaire.repository

import android.arch.persistence.room.*
import com.verbatoria.domain.questionnaire.model.QuestionnaireEntity

/**
 * @author n.remnev
 */

@Dao
interface QuestionnaireDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: QuestionnaireEntity)

    @Query("DELETE FROM QuestionnaireEntity WHERE eventId = :eventId")
    fun deleteByEventId(eventId: String)

    @Query("SELECT * FROM QuestionnaireEntity  WHERE eventId = :eventId")
    fun findByEventId(eventId: String): QuestionnaireEntity?

}