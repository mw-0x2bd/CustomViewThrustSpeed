package com.mw.rfniias.thrustspeed.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mw.rfniias.spreadexchange.aspreadclient.AEvent
import com.mw.rfniias.thrustspeed.model.repository.Repository
import kotlin.math.max

class ThrustSpeedViewModel(application: Application) : AndroidViewModel(application), AEvent.HandlerAEvent {


    private val numberOfThrust: Int = 2
    private val numberOfMeasuringSection: Int = 1

    private var currentThrust: Int = 0
    private var currentMeasuringSection: Int = 0

    private val currentTrafficLightList =
        Array(max(numberOfThrust, numberOfMeasuringSection)) { STATE_TRAFFIC_LIGHT.WHITE }

    private val recommendedTrafficLightList =
        Array(max(numberOfThrust, numberOfMeasuringSection)) { STATE_TRAFFIC_LIGHT.WHITE }

    private val currentSpeedList = Array(max(numberOfThrust, numberOfMeasuringSection)) { 0.0 }
    private val recommendedSpeedList = Array(max(numberOfThrust, numberOfMeasuringSection)) { 0.0 }

    var registration = true

    private val repository = Repository(application, this)

    private val currentTrafficLightMutableLiveData = MutableLiveData<STATE_TRAFFIC_LIGHT>()
    private val recommendedTrafficLightMutableLiveData = MutableLiveData<STATE_TRAFFIC_LIGHT>()
    private val currentSpeedMutableLiveData = MutableLiveData<Double>()
    private val recommendedSpeedMutableLiveData = MutableLiveData<Double>()
    private val recommendedPointMutableLiveData = MutableLiveData<STATE_POINT>()

    fun getCurrentTrafficLight(): LiveData<STATE_TRAFFIC_LIGHT> = currentTrafficLightMutableLiveData
    fun getRecommendedTrafficLight(): LiveData<STATE_TRAFFIC_LIGHT> = recommendedTrafficLightMutableLiveData
    fun getCurrentSpeed(): LiveData<Double> = currentSpeedMutableLiveData
    fun getRecommendedSpeed(): LiveData<Double> = recommendedSpeedMutableLiveData
    fun getRecommendedPoint(): LiveData<STATE_POINT> = recommendedPointMutableLiveData

    override fun processingAEvent(aEvent: AEvent) {
        when (aEvent.event) {

            AEvent.Event.ARS_KGM_CALC_VNAD -> {
                recommendedSpeedList[aEvent.number] = aEvent.value / 32.0 * 3.6
                recommendedTrafficLightList[aEvent.number] = when (aEvent.valueAdditional.toInt()) {
                    0 -> STATE_TRAFFIC_LIGHT.YELLOW
                    1 -> STATE_TRAFFIC_LIGHT.YELLOW_GREEN
                    2 -> STATE_TRAFFIC_LIGHT.GREEN
                    3 -> STATE_TRAFFIC_LIGHT.RED
                    else -> STATE_TRAFFIC_LIGHT.WHITE
                }
                send()
                println("$aEvent")
            }

            AEvent.Event.GAC_KGM_SVET -> {
                currentTrafficLightList[aEvent.number] = when (aEvent.value.toInt()) {
                    1 -> STATE_TRAFFIC_LIGHT.YELLOW
                    5 -> STATE_TRAFFIC_LIGHT.YELLOW_GREEN
                    4 -> STATE_TRAFFIC_LIGHT.GREEN
                    2 -> STATE_TRAFFIC_LIGHT.RED
                    else -> STATE_TRAFFIC_LIGHT.WHITE
                }
                send()
                println("$aEvent")
            }

            AEvent.Event.GAC_SP_NADVIG -> {
                currentThrust = aEvent.value.toInt()
                send()
                println("$aEvent")
            }

            AEvent.Event.GAC_KGM_NAD_SPEED -> {
                currentSpeedList[aEvent.number] = aEvent.value / 32.0 * 3.6
                send()
//                println("$aEvent")
            }

            AEvent.Event.GAC_SP_DSPG_NOMER_IU -> {
                currentMeasuringSection = aEvent.value.toInt()
                send()
                println("$aEvent")
            }

            else -> {
            }
        }
    }

    private fun send() {
        recommendedSpeedMutableLiveData.postValue(recommendedSpeedList[currentMeasuringSection])
        recommendedTrafficLightMutableLiveData.postValue(recommendedTrafficLightList[currentMeasuringSection])

        if (recommendedSpeedList[currentMeasuringSection] >= currentSpeedList[currentMeasuringSection] + 1.0) {
            recommendedPointMutableLiveData.postValue(STATE_POINT.UP)
        } else if (recommendedSpeedList[currentMeasuringSection] <= currentSpeedList[currentMeasuringSection] - 0.5) {
            recommendedPointMutableLiveData.postValue(STATE_POINT.DOWN)
        } else {
            recommendedPointMutableLiveData.postValue(STATE_POINT.NORMAL)
        }

        currentSpeedMutableLiveData.postValue(currentSpeedList[currentMeasuringSection])
        currentTrafficLightMutableLiveData.postValue(currentTrafficLightList[currentMeasuringSection])
    }

    fun connect() {
        if (registration) {
            repository.connect()
        }
    }

    fun disconnect() {
        repository.disconnect()
    }

    enum class STATE_TRAFFIC_LIGHT {
        YELLOW,
        YELLOW_GREEN,
        GREEN,
        RED,
        WHITE
    }

    enum class STATE_POINT {
        UP,
        NORMAL,
        DOWN
    }

//    private fun writeFile(data: String) {
//        var context: Context = getApplication()
//        var fos: FileOutputStream? = null
//        try {
//            fos = context.openFileOutput("emulTS.txt", Context.MODE_APPEND)
//            fos!!.write(data.toByteArray())
//        } catch (ex: IOException) {
//            Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
//        } finally {
//            try {
//                fos?.close()
//            } catch (ex: IOException) {
//                Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
//            }
//
//        }
//    }

}
