package com.mw.rfniias.thrustspeed.model.repository

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.mw.rfniias.spreadexchange.aspreadclient.AEvent
import com.mw.rfniias.spreadexchange.service.HandlerAEvent
import com.mw.rfniias.spreadexchange.service.SubscribeAEvent

class ASpreadClientServiceConnection(val handlerAEvent: AEvent.HandlerAEvent) : ServiceConnection {

    var connect = false

    private val ACTION_FOR_ASPREAD_START_SERVICE = "com.mw.rfniias.spreadexchange.service.ServiceASpreadClient.ACTION_FOR_SERVICE_ASPREAD_START_SERVICE"
    private val PACKAGE_NAME_ASPREAED = "com.mw.rfniias.spreadexchange"

    val intent: Intent = Intent(ACTION_FOR_ASPREAD_START_SERVICE)
            .setPackage(PACKAGE_NAME_ASPREAED)

    private var subscribeAEvent: SubscribeAEvent? = null

    override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
        connect = true
        subscribeAEvent = SubscribeAEvent.Stub.asInterface(iBinder)
        subscribeAEvent?.registerHandler(mHandlerAEvent)
    }

    override fun onServiceDisconnected(componentName: ComponentName) {
        connect = false
        try {
            unregisterHandler()
        } catch (e: Exception) {
            Log.d("TS", "Exception: ASpreadClientServiceConnection::onServiceDisconnected() -> unregisterHandler()")
        }
    }

    fun unregisterHandler() = subscribeAEvent?.unregisterHandler(mHandlerAEvent)

    private val mHandlerAEvent: HandlerAEvent = object : HandlerAEvent.Stub() {
        @Throws(RemoteException::class)
        override fun processing(aEvent: AEvent) {
            if (IU_TO_DSPG_0 == aEvent.group ||
                DATA_ARS == aEvent.group ||
                DATA_GAC == aEvent.group
            ) {
//                println("TS: aEvent.event = " + aEvent.event)
            }
            handlerAEvent.processingAEvent(aEvent)
        }

        private val IU_TO_DSPG_0 = "iu_to_dspg_0"
        private val DATA_ARS = "data_ars"
        private val DATA_GAC = "data_gac"
    }
}