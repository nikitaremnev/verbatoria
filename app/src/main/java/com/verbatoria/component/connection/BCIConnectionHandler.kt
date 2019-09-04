package com.verbatoria.component.connection

import android.util.Log
import com.neurosky.connection.ConnectionStates
import com.neurosky.connection.DataType.MindDataType
import com.neurosky.connection.EEGPower
import com.neurosky.connection.TgStreamHandler

/**
 * @author n.remnev
 */

interface BCIConnectionHandler : TgStreamHandler {

    fun bluetoothDisabled()

    fun setDataCallback(dataCallback: BCIDataCallback?)

    fun setStateCallback(stateCallback: BCIConnectionStateCallback?)

}

class BCIConnectionHandlerImpl: BCIConnectionHandler {

    private var dataCallback: BCIDataCallback? = null

    private var stateCallback: BCIConnectionStateCallback? = null

    //region BCIConnectionHandler

    override fun bluetoothDisabled() {
        stateCallback?.onBluetoothDisabled()
    }

    override fun setDataCallback(dataCallback: BCIDataCallback?) {
        this.dataCallback = dataCallback
    }

    override fun setStateCallback(stateCallback: BCIConnectionStateCallback?) {
        this.stateCallback = stateCallback
    }

    //endregion

    //region TgStreamHandler

    override fun onStatesChanged(connectionState: Int) {
        Log.e("test", "BCIConnectionHandler connectionState $connectionState")
        when (connectionState) {
            ConnectionStates.STATE_CONNECTING ->
                stateCallback?.onConnecting()
            ConnectionStates.STATE_CONNECTED ->
                stateCallback?.onConnected()
            ConnectionStates.STATE_DISCONNECTED, ConnectionStates.STATE_GET_DATA_TIME_OUT ->
                stateCallback?.onDisconnected()
            ConnectionStates.STATE_ERROR, ConnectionStates.STATE_FAILED ->
                stateCallback?.onConnectionFailed()
            ConnectionStates.STATE_WORKING ->
                stateCallback?.onWorking()
            ConnectionStates.STATE_RECORDING_START ->
                stateCallback?.onRecordingStarted()
        }
    }

    override fun onChecksumFail(bytes: ByteArray, i: Int, i1: Int) {
        //empty
    }

    override fun onRecordFail(i: Int) {
        //empty
    }

    override fun onDataReceived(dataCode: Int, data: Int, eegPowerObject: Any) {
        when (dataCode) {
            MindDataType.CODE_ATTENTION -> {
                dataCallback?.onAttentionDataReceived(data)
            }
            MindDataType.CODE_MEDITATION -> {
                dataCallback?.onMediationDataReceived(data)
            }
            MindDataType.CODE_EEGPOWER -> {
                (eegPowerObject as? EEGPower)?.let { eegPower ->
                    dataCallback?.onEEGDataReceivedCallback(eegPower)
                }
            }
        }
    }

    //endregion

}