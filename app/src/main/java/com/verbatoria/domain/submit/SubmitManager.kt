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
import com.verbatoria.utils.FileUtils
import com.verbatoria.utils.PreferencesStorage
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

    fun sendData(eventId: String)

    fun finishSession(eventId: String)

    fun cleanData(eventId: String)

}

class SubmitManagerImpl(
    private val bciDataManager: BCIDataManager,
    private val questionnaireManager: QuestionnaireManager,
    private val activityManager: ActivitiesManager,
    private val submitEndpoint: SubmitEndpoint
) : SubmitManager {

    override fun sendData(eventId: String) {
        val bciData = bciDataManager.findAllByEventId(eventId)
        val versionName = BuildConfig.VERSION_NAME
        val questionnaire = questionnaireManager.getQuestionnaireByEventId(eventId)
        val currentLocale = PreferencesStorage.getInstance().currentLocale

        val bciDataMutableList = bciData.map { bciData ->
            BCIDataItemParamsDto(
                eventId = bciData.eventId,
                activityCode = bciData.activityCode,
                questionnaire = "",
                applicationVersion = versionName,
                createdAt = Date(bciData.timestamp).formatToServerTime(),
                attention = bciData.attention,
                mediation = bciData.mediation,
                delta = bciData.delta,
                theta = bciData.theta,
                lowAlpha = bciData.lowAlpha,
                highAlpha = bciData.highAlpha,
                lowBeta = bciData.lowBeta,
                highBeta = bciData.highBeta,
                lowGamma = bciData.lowGamma,
                middleGamma = bciData.middleGamma
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

        val reportFile = File(FileUtils.getApplicationDirectory(), getReportFileName(eventId))
        if (!reportFile.exists()) {
            reportFile.createNewFile()
        }

        FileOutputStream(reportFile).use { fileOutputStream ->
            OutputStreamWriter(fileOutputStream).use { outputStreamWriter ->
                outputStreamWriter.append(Gson().toJson(BCIDataFileParamsDto(bciDataMutableList)))
            }
        }

        submitEndpoint.sendData(
            sessionId = eventId,
            body = RequestBody.create(MediaType.parse("application/json"), reportFile)
        )
    }

    override fun finishSession(eventId: String) {
        submitEndpoint.finishSession(eventId)
    }

    override fun cleanData(eventId: String) {
        bciDataManager.deleteAllByEventId(eventId)
        activityManager.deleteByEventId(eventId)
        val reportFile = File(FileUtils.getApplicationDirectory(), getReportFileName(eventId))
        if (reportFile.exists()) {
            reportFile.delete()
        }
    }

    private fun getReportFileName(eventId: String): String =
        REPORT_FILE_NAME_PREFIX + eventId + JSON_FILE_EXTENSION

}