package com.verbatoria.domain.questionnaire.model

/**
 * @author n.remnev
 */

class Questionnaire(
    val sessionId: String,
    var linguisticQuestionAnswer: QuestionAnswer = QuestionAnswer.NO_ANSWER,
    var logicMathematicalAnswer: QuestionAnswer = QuestionAnswer.NO_ANSWER,
    var musicAnswer: QuestionAnswer = QuestionAnswer.NO_ANSWER,
    var spatialAnswer: QuestionAnswer = QuestionAnswer.NO_ANSWER,
    var bodyKinestheticAnswer: QuestionAnswer = QuestionAnswer.NO_ANSWER,
    var understandingPeopleAnswer: QuestionAnswer = QuestionAnswer.NO_ANSWER,
    var understandingYourselfAnswer: QuestionAnswer = QuestionAnswer.NO_ANSWER,
    var reportType: ReportType = ReportType.NOT_SELECTED,
    var includeAttentionMemory: QuestionYesOrNoAnswer = QuestionYesOrNoAnswer.NO_ANSWER,
    var includeHobby: QuestionYesOrNoAnswer = QuestionYesOrNoAnswer.NO_ANSWER
)

enum class QuestionType {

    LINGUISTIC, LOGIC_MATHEMATICAL, MUSIC, SPATIAL, BODY_KINESTHETIC, UNDERSTANDING_PEOPLE, UNDERSTANDING_YOURSELF;

    companion object {

        fun valueOf(ordinal: Int) =
            when (ordinal) {
                LINGUISTIC.ordinal -> LINGUISTIC
                LOGIC_MATHEMATICAL.ordinal -> LOGIC_MATHEMATICAL
                MUSIC.ordinal -> MUSIC
                SPATIAL.ordinal -> SPATIAL
                BODY_KINESTHETIC.ordinal -> BODY_KINESTHETIC
                UNDERSTANDING_PEOPLE.ordinal -> UNDERSTANDING_PEOPLE
                UNDERSTANDING_YOURSELF.ordinal -> UNDERSTANDING_YOURSELF
                else -> throw IllegalArgumentException("Wrong ordinal for QuestionType")
            }

    }

}

enum class QuestionAnswer(val value: Int) {

    NO_ANSWER(0), ANSWER_10(10), ANSWER_20(20), ANSWER_40(40), ANSWER_60(60), ANSWER_90(90);

    companion object {

        fun valueOf(value: Int) =
            when (value) {
                NO_ANSWER.value -> NO_ANSWER
                ANSWER_10.value -> ANSWER_10
                ANSWER_20.value -> ANSWER_20
                ANSWER_40.value -> ANSWER_40
                ANSWER_60.value -> ANSWER_60
                ANSWER_90.value -> ANSWER_90
                else -> throw IllegalArgumentException("Wrong ordinal for QuestionAnswer")
            }

    }

}

enum class QuestionYesOrNoAnswer(val value: Int) {

    NO_ANSWER(-1), ANSWER_YES(1), ANSWER_NO(0);

    companion object {

        fun valueOf(value: Int) =
            when (value) {
                NO_ANSWER.value -> NO_ANSWER
                ANSWER_YES.value -> ANSWER_YES
                ANSWER_NO.value -> ANSWER_NO
                else -> throw IllegalArgumentException("Wrong ordinal for QuestionYesOrNoAnswer")
            }

    }

}

enum class ReportType(val value: Int) {

    NOT_SELECTED(-1), TYPE_0(0), TYPE_1(1), TYPE_2(2), TYPE_3(3),
    TYPE_4(4), TYPE_5(5), TYPE_6(6), TYPE_7(7), TYPE_8(8), TYPE_9(9),
    TYPE_10(10);

    companion object {

        fun valueOf(value: Int) =
            when (value) {
                NOT_SELECTED.value -> NOT_SELECTED
                TYPE_0.value -> TYPE_0
                TYPE_1.value -> TYPE_1
                TYPE_2.value -> TYPE_2
                TYPE_3.value -> TYPE_3
                TYPE_4.value -> TYPE_4
                TYPE_5.value -> TYPE_5
                TYPE_6.value -> TYPE_6
                TYPE_7.value -> TYPE_7
                TYPE_8.value -> TYPE_8
                TYPE_9.value -> TYPE_9
                TYPE_10.value -> TYPE_10
                else -> throw IllegalArgumentException("Wrong ordinal for ReportType")
            }

    }

}