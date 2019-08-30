package com.verbatoria.domain.questionnaire.repository

import com.verbatoria.domain.questionnaire.model.*
import com.verbatoria.infrastructure.conversion.DatabaseConverter

/**
 * @author n.remnev
 */

class QuestionnaireConverter : DatabaseConverter<QuestionnaireEntity, Questionnaire>() {

    override fun toEntity(domain: Questionnaire): QuestionnaireEntity =
        with(domain) {
            QuestionnaireEntity(
                eventId = eventId,
                linguisticQuestionAnswer = linguisticQuestionAnswer.value,
                logicMathematicalAnswer = logicMathematicalAnswer.value,
                musicAnswer = musicAnswer.value,
                spatialAnswer = spatialAnswer.value,
                bodyKinestheticAnswer = bodyKinestheticAnswer.value,
                understandingPeopleAnswer = understandingPeopleAnswer.value,
                understandingYourselfAnswer = understandingYourselfAnswer.value,
                reportType = reportType.value,
                includeAttentionMemory = includeAttentionMemory.value,
                includeHobby = includeHobby.value
            )
        }

    override fun toDomain(entity: QuestionnaireEntity): Questionnaire =
        with(entity) {
            Questionnaire(
                eventId = eventId,
                linguisticQuestionAnswer = QuestionAnswer.valueOf(linguisticQuestionAnswer),
                logicMathematicalAnswer = QuestionAnswer.valueOf(logicMathematicalAnswer),
                musicAnswer = QuestionAnswer.valueOf(musicAnswer),
                spatialAnswer = QuestionAnswer.valueOf(spatialAnswer),
                bodyKinestheticAnswer = QuestionAnswer.valueOf(bodyKinestheticAnswer),
                understandingPeopleAnswer = QuestionAnswer.valueOf(understandingPeopleAnswer),
                understandingYourselfAnswer = QuestionAnswer.valueOf(understandingYourselfAnswer),
                reportType = ReportType.valueOf(reportType),
                includeAttentionMemory = QuestionYesOrNoAnswer.valueOf(includeAttentionMemory),
                includeHobby = QuestionYesOrNoAnswer.valueOf(includeHobby)
            )
        }

}