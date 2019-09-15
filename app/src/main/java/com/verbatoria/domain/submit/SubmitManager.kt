package com.verbatoria.domain.submit

import com.google.gson.Gson
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.domain.activities.manager.ActivitiesManager
import com.verbatoria.domain.bci_data.manager.BCIDataManager
import com.verbatoria.domain.child.model.Child
import com.verbatoria.domain.late_send.manager.LateSendManager
import com.verbatoria.domain.questionnaire.manager.QuestionnaireManager
import com.verbatoria.domain.questionnaire.model.QuestionYesOrNoAnswer
import com.verbatoria.domain.schedule.model.TimeSlot
import com.verbatoria.infrastructure.extensions.MILLISECONDS_IN_SECOND
import com.verbatoria.infrastructure.extensions.formatToServerTime
import com.verbatoria.infrastructure.retrofit.endpoints.submit.SubmitEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.submit.model.params.BCIDataFileParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.submit.model.params.BCIDataItemParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.submit.model.params.StartSessionParamsDto
import com.verbatoria.utils.FileUtils
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.*

/**
 * @author n.remnev
 */

private const val REPORT_FILE_NAME_PREFIX = "report_"
private const val JSON_FILE_EXTENSION = ".json"
private const val FIRST_POSITION_INDEX = 0

interface SubmitManager {

    fun startSession(eventId: String, reportId: String, child: Child, timeSlot: TimeSlot): String

    fun sendData(sessionId: String)

    fun finishSession(sessionId: String)

    fun cleanData(sessionId: String)

}

class SubmitManagerImpl(
    private val bciDataManager: BCIDataManager,
    private val questionnaireManager: QuestionnaireManager,
    private val activityManager: ActivitiesManager,
    private val lateSendManager: LateSendManager,
    private val submitEndpoint: SubmitEndpoint
) : SubmitManager {

    override fun startSession(eventId: String, reportId: String, child: Child, timeSlot: TimeSlot): String {
        val sessionId = submitEndpoint.startSession(
            StartSessionParamsDto(eventId)
        ).id

        if (lateSendManager.findLateSend(sessionId) == null) {
            lateSendManager.createLateSend(
                eventId = eventId,
                sessionId = sessionId,
                reportId = reportId,
                childName = child.name,
                childAge = child.age,
                startDate = timeSlot.startTime,
                endDate = timeSlot.endTime
            )
        }

        return sessionId
    }


    override fun sendData(sessionId: String) {
        val bciData = bciDataManager.findAllBySessionId(sessionId)
        val versionName = BuildConfig.VERSION_NAME
        val questionnaire = questionnaireManager.getQuestionnaireBySessionId(sessionId)
        val currentLocale = "ru"//PreferencesStorage.getInstance().currentLocale
        var firstTimeStamp = bciData.firstOrNull()?.timestamp ?: System.currentTimeMillis()

        val bciDataMutableList = bciData.map { bciDataItem ->
            BCIDataItemParamsDto(
                sessionId = sessionId,
                activityCode = bciDataItem.activityCode,
                questionnaire = "",
                applicationVersion = versionName,
                createdAt = Date(bciDataItem.timestamp).formatToServerTime(),
                attention = bciDataItem.attention,
                mediation = bciDataItem.mediation,
                delta = bciDataItem.delta,
                theta = bciDataItem.theta,
                lowAlpha = bciDataItem.lowAlpha,
                highAlpha = bciDataItem.highAlpha,
                lowBeta = bciDataItem.lowBeta,
                highBeta = bciDataItem.highBeta,
                lowGamma = bciDataItem.lowGamma,
                middleGamma = bciDataItem.middleGamma
            )
        }.toMutableList()

        firstTimeStamp -= MILLISECONDS_IN_SECOND

        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = currentLocale,
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = if (questionnaire.includeHobby == QuestionYesOrNoAnswer.NO_ANSWER) {
                    "0"
                } else {
                    questionnaire.includeHobby.value.toString()
                },
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.includeAttentionMemory.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.reportType.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.understandingYourselfAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.understandingPeopleAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.bodyKinestheticAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.spatialAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.musicAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.logicMathematicalAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.linguisticQuestionAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        val reportFile = File(FileUtils.getApplicationDirectory(), getReportFileName(sessionId))
        if (!reportFile.exists()) {
            reportFile.createNewFile()
        }

        FileOutputStream(reportFile).use { fileOutputStream ->
            OutputStreamWriter(fileOutputStream).use { outputStreamWriter ->
                outputStreamWriter.append(Gson().toJson(BCIDataFileParamsDto(bciDataMutableList)))
            }
        }

        submitEndpoint.sendData(
            sessionId = sessionId,
            body = RequestBody.create(MediaType.parse("application/json"), reportFile)
        )
    }

    override fun finishSession(sessionId: String) {
        submitEndpoint.finishSession(sessionId)
    }

    override fun cleanData(sessionId: String) {
        bciDataManager.deleteAllBySessionId(sessionId)
        activityManager.deleteBySessionId(sessionId)
        questionnaireManager.deleteQuestionnaireBySessionId(sessionId)
        lateSendManager.deleteLateSendBySessionId(sessionId)
        val reportFile = File(FileUtils.getApplicationDirectory(), getReportFileName(sessionId))
        if (reportFile.exists()) {
            reportFile.delete()
        }
    }

    private fun getReportFileName(sessionId: String): String =
        REPORT_FILE_NAME_PREFIX + sessionId + JSON_FILE_EXTENSION

}