package com.verbatoria.domain.questionnaire.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author n.remnev
 */

@Entity(tableName = "QuestionnaireEntity")
class QuestionnaireEntity(
    @PrimaryKey
    val sessionId: String,
    val linguisticQuestionAnswer: Int,
    val logicMathematicalAnswer: Int,
    val musicAnswer: Int,
    val spatialAnswer: Int,
    val bodyKinestheticAnswer: Int,
    val understandingPeopleAnswer: Int,
    val understandingYourselfAnswer: Int,
    val reportType: Int,
    val includeAttentionMemory: Int,
    val includeHobby: Int
)