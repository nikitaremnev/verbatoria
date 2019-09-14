package com.verbatoria.component.connection

import android.bluetooth.BluetoothAdapter
import android.util.Log
import com.neurosky.connection.TgStreamReader

/**
 * @author n.remnev
 */

interface BCIConnectionController {

    fun startConnection()

    fun startWriting()

    fun stopWriting()

    fun stopConnection()

}

class BCIConnectionControllerImpl(
    private val bciConnectionHandler: BCIConnectionHandler
) : BCIConnectionController {

    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private var tgStreamReader: TgStreamReader? = null

    override fun startConnection() {
        Log.e("test", "BCIConnectionController startConnection")

        if (bluetoothAdapter != null && bluetoothAdapter?.isEnabled == true) {
            tgStreamReader?.let { streamReader ->
                if (streamReader.isBTConnected) {
                    streamReader.stop()
                    streamReader.close()
                }
            }
            tgStreamReader = TgStreamReader(bluetoothAdapter, bciConnectionHandler)
            tgStreamReader?.connect()
        } else {
            Log.e("test", "BCIConnectionController bluetoothDisabled")
            bciConnectionHandler.bluetoothDisabled()
        }
    }

    override fun startWriting() {
        tgStreamReader?.start()
    }

    override fun stopWriting() {
        tgStreamReader?.stop()
    }

    override fun stopConnection() {
        tgStreamReader?.let { streamReader ->
            if (streamReader.isBTConnected) {
                streamReader.stop()
                streamReader.close()
            }
        }
    }

}