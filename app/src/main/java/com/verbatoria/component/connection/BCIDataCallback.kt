package com.verbatoria.component.connection

import com.neurosky.connection.EEGPower

/**
 * @author n.remnev
 */

interface BCIDataCallback {

    fun onAttentionDataReceived(attentionValue: Int)

    fun onMediationDataReceived(mediationValue: Int)

    fun onEEGDataReceivedCallback(eegPower: EEGPower)

}