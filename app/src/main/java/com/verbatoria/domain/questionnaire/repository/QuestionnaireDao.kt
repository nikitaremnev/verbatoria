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

    @Query("DELETE FROM QuestionnaireEntity WHERE sessionId = :sessionId")
    fun deleteBySessionId(sessionId: String)

    @Query("SELECT * FROM QuestionnaireEntity  WHERE sessionId = :sessionId")
    fun findBySessionId(sessionId: String): QuestionnaireEntity?

}