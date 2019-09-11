package com.mw.rfniias.thrustspeed.model.repository

import android.content.Context
import android.util.Log
import com.mw.rfniias.spreadexchange.aspreadclient.AEvent

class Repository (val context: Context, handlerAEvent: AEvent.HandlerAEvent) {

    private val emulation = true

    private val aSpreadClientServiceConnection = ASpreadClientServiceConnection(handlerAEvent)

    private fun connectASpreadClient(){
        if (!aSpreadClientServiceConnection.connect) {
            context.bindService(aSpreadClientServiceConnection.intent, aSpreadClientServiceConnection, Context.BIND_AUTO_CREATE)
            Log.d("TS", "connect::bindService")
        }
    }

    private fun disconnectASpreadClient(){
        if (aSpreadClientServiceConnection.connect) {
            aSpreadClientServiceConnection.unregisterHandler()
            context.unbindService(aSpreadClientServiceConnection)
            aSpreadClientServiceConnection.connect = false
            Log.d("TS", "disconnect::unbindService")
        }
    }

    fun connect() {
        connectASpreadClient()
    }

    fun disconnect() {
        disconnectASpreadClient()
    }
}