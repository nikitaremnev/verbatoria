package com.verbatoria.component.connection

/**
 * @author n.remnev
 */

interface NeurodataConnectionStateCallback {

    fun onConnecting()

    fun onConnected()

    fun onWorking()

    fun onRecordingStarted()

    fun onConnectionFailed()

    fun onDisconnected()

    fun onBluetoothDisabled()

}