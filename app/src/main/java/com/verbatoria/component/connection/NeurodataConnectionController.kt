package com.verbatoria.component.connection

import android.bluetooth.BluetoothAdapter
import com.neurosky.connection.TgStreamReader

/**
 * @author n.remnev
 */

interface NeurodataConnectionController {

    fun startConnection()

    fun stopConnection()

}

class NeurodataConnectionControllerImpl(
    private val neurodataConnectionHandler: NeurodataConnectionHandler
) : NeurodataConnectionController {

    private var bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private var tgStreamReader: TgStreamReader? = null

    override fun startConnection() {
        if (bluetoothAdapter != null && bluetoothAdapter?.isEnabled == true) {
            tgStreamReader?.let { streamReader ->
                if (streamReader.isBTConnected) {
                    streamReader.stop()
                    streamReader.close()
                }
                tgStreamReader = TgStreamReader(bluetoothAdapter, neurodataConnectionHandler)
                tgStreamReader?.connect()
            }
        } else {
            neurodataConnectionHandler.bluetoothDisabled()
        }
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