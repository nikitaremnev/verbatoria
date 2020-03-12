package com.verbatoria.domain.submit

import android.bluetooth.BluetoothAdapter
import com.google.gson.Gson
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.domain.activities.manager.ActivitiesManager
import com.verbatoria.domain.bci_data.manager.BCIDataManager
import com.verbatoria.domain.child.model.Child
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.domain.late_send.manager.LateSendManager
import com.verbatoria.domain.questionnaire.manager.QuestionnaireManager
import com.verbatoria.domain.questionnaire.model.QuestionYesOrNoAnswer
import com.verbatoria.domain.schedule.model.TimeSlot
import com.verbatoria.infrastructure.extensions.MILLISECONDS_IN_SECOND
import com.verbatoria.infrastructure.extensions.formatToServerTime
import com.verbatoria.infrastructure.file.FileUtil
import com.verbatoria.infrastructure.retrofit.endpoints.report.ReportEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.submit.SubmitEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.submit.model.params.BCIDataFileParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.submit.model.params.BCIDataItemParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.submit.model.params.StartSessionParamsDto
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
private const val APPLICATION_JSON_MEDIA_TYPE = "application/json"

private const val FIRST_POSITION_INDEX = 0

interface SubmitManager {

    fun startSession(eventId: String, reportId: String, child: Child, timeSlot: TimeSlot): String

    fun sendData(sessionId: String)

    fun sendDataFromReadyFile(sessionId: String)

    fun finishSession(sessionId: String)

    fun cleanData(sessionId: String)

}

class SubmitManagerImpl(
    private val bciDataManager: BCIDataManager,
    private val questionnaireManager: QuestionnaireManager,
    private val activityManager: ActivitiesManager,
    private val lateSendManager: LateSendManager,
    private val settingsRepository: SettingsRepository,
    private val submitEndpoint: SubmitEndpoint,
    private val reportEndpoint: ReportEndpoint,
    private val fileUtil: FileUtil
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
        val currentLocale = settingsRepository.getCurrentLocale()
        var firstTimeStamp = bciData.firstOrNull()?.timestamp ?: System.currentTimeMillis()

        val bciDataMutableList = bciData.map { bciDataItem ->
            BCIDataItemParamsDto(
                guid = bciDataItem.guid,
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
                middleGamma = bciDataItem.middleGamma,
                bciMacAddress = bciDataItem.connectedDeviceMacAddress
            )
        }.toMutableList()

        //11 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = currentLocale,
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        //10 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
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

        //9 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        //8 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.reportType.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        //7 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = if (questionnaire.includeAttentionMemory == QuestionYesOrNoAnswer.NO_ANSWER) {
                    "0"
                } else {
                    questionnaire.includeAttentionMemory.value.toString()
                },
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        //6 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.understandingYourselfAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        //5 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.understandingPeopleAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        //4 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.bodyKinestheticAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        //3 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.spatialAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        //2 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.musicAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        //1 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.logicMathematicalAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        //0 index
        firstTimeStamp -= MILLISECONDS_IN_SECOND
        bciDataMutableList.add(
            FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(
                guid = firstTimeStamp.toString(),
                sessionId = sessionId,
                applicationVersion = versionName,
                questionnaire = questionnaire.linguisticQuestionAnswer.value.toString(),
                createdAt = Date(firstTimeStamp).formatToServerTime()
            )
        )

        val reportFile = File(fileUtil.getApplicationDirectory(), getReportFileName(sessionId))
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
            body = RequestBody.create(MediaType.parse(APPLICATION_JSON_MEDIA_TYPE), reportFile)
        )

        if (questionnaire.includeHobby == QuestionYesOrNoAnswer.ANSWER_YES) {
            reportEndpoint.includeHobby(sessionId)
        }
    }

    override fun sendDataFromReadyFile(sessionId: String) {
        val reportFile = File(fileUtil.getApplicationDirectory(), getReportFileName(sessionId))
        if (!reportFile.exists()) {
            reportFile.createNewFile()
        }
        submitEndpoint.sendData(
            sessionId = sessionId,
            body = RequestBody.create(MediaType.parse(APPLICATION_JSON_MEDIA_TYPE), reportFile)
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
        val reportFile = File(fileUtil.getApplicationDirectory(), getReportFileName(sessionId))
        if (reportFile.exists()) {
            reportFile.delete()
        }
    }

    private fun getReportFileName(sessionId: String): String =
        REPORT_FILE_NAME_PREFIX + sessionId + JSON_FILE_EXTENSION

}