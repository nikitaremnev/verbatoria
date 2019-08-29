package com.verbatoria.domain.report.model

/**
 * @author nikitaremnev
 */

enum class ReportStatus(
    val serverStatus: String
) {

    NEW("new"), CANCELED("cancelled"), UPLOADED("uploaded"), READY("ready"), SENT("sent");

    companion object {

        fun valueOfWithDefault(serverStatus: String): ReportStatus =
            when (serverStatus) {
                NEW.serverStatus -> NEW
                CANCELED.serverStatus -> CANCELED
                UPLOADED.serverStatus -> UPLOADED
                READY.serverStatus -> READY
                SENT.serverStatus -> SENT
                else -> NEW
            }

    }

}