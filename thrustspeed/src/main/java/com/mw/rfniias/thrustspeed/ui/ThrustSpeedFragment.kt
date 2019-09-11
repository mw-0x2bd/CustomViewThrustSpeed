package com.mw.rfniias.thrustspeed.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.mw.rfniias.thrustspeed.R
import com.mw.rfniias.thrustspeed.model.ThrustSpeedViewModel
import com.mw.rfniias.thrustspeed.ui.component.Point
import com.mw.rfniias.thrustspeed.ui.component.TrafficLight

class ThrustSpeedFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = ThrustSpeedFragment()
    }

    private lateinit var viewModel: ThrustSpeedViewModel

    private lateinit var point: Point
    private lateinit var currentTrafficLight: TrafficLight
    private lateinit var recommendedTrafficLight: TrafficLight
    private lateinit var currentSpeed: TextView
    private lateinit var recommendedSpeed: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_thrust_speed, container, false)
        point = view.findViewById(R.id.recommended_point)
        currentTrafficLight = view.findViewById(R.id.current_traffic_light)
        recommendedTrafficLight = view.findViewById(R.id.recommended_traffic_light)
        currentSpeed = view.findViewById(R.id.current_speed_text_view)
        recommendedSpeed = view.findViewById(R.id.recommended_speed_text_view)

        val f: (view: View) -> Unit = {
            when (it.id) {
                R.id.up_button -> point.up()
                R.id.normal_button -> point.normal()
                R.id.down_button -> point.down()

                R.id.white_button -> currentTrafficLight.white()
                R.id.green_button -> currentTrafficLight.green()
                R.id.red_button -> currentTrafficLight.red()
                R.id.yellow_button -> currentTrafficLight.yellow()
            }
        }

        view.findViewById<Button>(R.id.up_button)?.setOnClickListener(f)
        view.findViewById<Button>(R.id.normal_button)?.setOnClickListener(f)
        view.findViewById<Button>(R.id.down_button)?.setOnClickListener(f)

        view.findViewById<Button>(R.id.white_button)?.setOnClickListener(f)
        view.findViewById<Button>(R.id.green_button)?.setOnClickListener(f)
        view.findViewById<Button>(R.id.red_button)?.setOnClickListener(f)
        view.findViewById<Button>(R.id.yellow_button)?.setOnClickListener(f)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(ThrustSpeedViewModel::class.java)
        viewModel.getRecommendedTrafficLight().observe(this, Observer {
            when (it) {
                ThrustSpeedViewModel.STATE_TRAFFIC_LIGHT.YELLOW -> recommendedTrafficLight.yellow()
                ThrustSpeedViewModel.STATE_TRAFFIC_LIGHT.YELLOW_GREEN -> recommendedTrafficLight.yellowGreen()
                ThrustSpeedViewModel.STATE_TRAFFIC_LIGHT.GREEN -> recommendedTrafficLight.green()
                ThrustSpeedViewModel.STATE_TRAFFIC_LIGHT.RED -> recommendedTrafficLight.red()
                else -> recommendedTrafficLight.white()
            }
        })

        viewModel.getRecommendedSpeed().observe(this, Observer {
            recommendedSpeed.text = ("%.1f".format(it))
        })

        viewModel.getCurrentTrafficLight().observe(this, Observer {
            when (it) {
                ThrustSpeedViewModel.STATE_TRAFFIC_LIGHT.YELLOW -> currentTrafficLight.yellow()
                ThrustSpeedViewModel.STATE_TRAFFIC_LIGHT.YELLOW_GREEN -> currentTrafficLight.yellowGreen()
                ThrustSpeedViewModel.STATE_TRAFFIC_LIGHT.GREEN -> currentTrafficLight.green()
                ThrustSpeedViewModel.STATE_TRAFFIC_LIGHT.RED -> currentTrafficLight.red()
                else -> currentTrafficLight.white()
            }
        })

        viewModel.getCurrentSpeed().observe(this, Observer {
            currentSpeed.text = ("%.1f".format(it))
        })

        viewModel.getRecommendedPoint().observe(this, Observer {
            when(it) {
                ThrustSpeedViewModel.STATE_POINT.UP -> point.up()
                ThrustSpeedViewModel.STATE_POINT.NORMAL -> point.normal()
                ThrustSpeedViewModel.STATE_POINT.DOWN -> point.down()
            }
        })
    }

}
