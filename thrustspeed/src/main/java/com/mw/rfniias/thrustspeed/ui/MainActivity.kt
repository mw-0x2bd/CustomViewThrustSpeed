package com.mw.rfniias.thrustspeed.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.mw.rfniias.thrustspeed.R
import com.mw.rfniias.thrustspeed.model.ThrustSpeedViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var model :ThrustSpeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        model = ViewModelProviders.of(this).get(ThrustSpeedViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ThrustSpeedFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onStart() {
        super.onStart()
        model.connect()
    }

    override fun onStop() {
        super.onStop()
        model.disconnect()
    }

}
