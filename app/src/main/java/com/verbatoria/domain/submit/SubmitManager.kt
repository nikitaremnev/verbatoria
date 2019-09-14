package com.verbatoria.domain.submit

import com.google.gson.Gson
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.domain.activities.manager.ActivitiesManager
import com.verbatoria.domain.bci_data.manager.BCIDataManager
import com.verbatoria.domain.questionnaire.manager.QuestionnaireManager
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

    fun startSession(eventId: String): String

    fun sendData(sessionId: String)

    fun finishSession(sessionId: String)

    fun cleanData(sessionId: String)

}

class SubmitManagerImpl(
    private val bciDataManager: BCIDataManager,
    private val questionnaireManager: QuestionnaireManager,
    private val activityManager: ActivitiesManager,
    private val submitEndpoint: SubmitEndpoint
) : SubmitManager {

    override fun startSession(eventId: String): String =
        submitEndpoint.startSession(
            StartSessionParamsDto(eventId)
        ).id

    override fun sendData(sessionId: String) {
        val bciData = bciDataManager.findAllBySessionId(sessionId)
        val versionName = BuildConfig.VERSION_NAME
        val questionnaire = questionnaireManager.getQuestionnaireBySessionId(sessionId)
        val currentLocale = "ru"//PreferencesStorage.getInstance().currentLocale

        val bciDataMutableList = bciData.map { bciDataItem ->
            BCIDataItemParamsDto(
                sessionId = bciDataItem.sessionId,
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

        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = currentLocale))
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = questionnaire.includeHobby.value.toString()))
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto())
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = questionnaire.includeAttentionMemory.value.toString()))
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = questionnaire.reportType.value.toString()))
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = questionnaire.understandingYourselfAnswer.value.toString()))
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = questionnaire.understandingPeopleAnswer.value.toString()))
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = questionnaire.bodyKinestheticAnswer.value.toString()))
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = questionnaire.spatialAnswer.value.toString()))
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = questionnaire.musicAnswer.value.toString()))
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = questionnaire.logicMathematicalAnswer.value.toString()))
        bciDataMutableList.add(FIRST_POSITION_INDEX,
            BCIDataItemParamsDto(questionnaire = questionnaire.linguisticQuestionAnswer.value.toString()))

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
        val reportFile = File(FileUtils.getApplicationDirectory(), getReportFileName(sessionId))
        if (reportFile.exists()) {
            reportFile.delete()
        }
    }

    private fun getReportFileName(sessionId: String): String =
        REPORT_FILE_NAME_PREFIX + sessionId + JSON_FILE_EXTENSION

}