package com.zeronine.project1.screen.order

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.zeronine.project1.R
import com.zeronine.project1.databinding.ActivityDeliveryBinding

class DeliveryActivity: AppCompatActivity() {

    private lateinit var deliveryBinding: ActivityDeliveryBinding
    private lateinit var map:GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        deliveryBinding = ActivityDeliveryBinding.inflate(layoutInflater)
        val view = deliveryBinding.root
        setContentView(view)

        //setupGoogleMap()
        //showRecruiterLocation()

    }

//    private fun setupGoogleMap() {
//        val mapFragment =
//            supportFragmentManager.findFragmentById(R.id.showRecruiterLocation) as SupportMapFragment
//        //mapFragment.getMapAsync(this)
//
//    }

//    private fun showRecruiterLocation() {
//
//
//    }

}