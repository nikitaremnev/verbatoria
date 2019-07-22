package com.verbatoria.business.report

/**
 * @author nikitaremnev
 */

enum class ReportStatus(
    val serverStatus: String
) {

    NEW("new"), CANCELLED("cancelled"), UPLOADED("uploaded"), READY("ready"), SENT("sent");

    companion object {

        fun valueOfWithDefault(serverStatus: String): ReportStatus =
            when (serverStatus) {
                NEW.serverStatus -> NEW
                CANCELLED.serverStatus -> CANCELLED
                UPLOADED.serverStatus -> UPLOADED
                READY.serverStatus -> READY
                SENT.serverStatus -> SENT
                else -> NEW
            }

    }

}