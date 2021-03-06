package com.zeronine.project1.screen.order

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.R
import com.zeronine.project1.data.DB.DBKey
import com.zeronine.project1.databinding.ActivityDeliveryBinding
import com.zeronine.project1.screen.home.make.currentGroupSettingID
import com.zeronine.project1.widget.model.GroupSettingModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class DeliveryActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var deliveryBinding: ActivityDeliveryBinding
    private lateinit var map:GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentMarker : Marker? = null

    private lateinit var groupSettingDB: DatabaseReference

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val groupSettingModel = snapshot.getValue(GroupSettingModel::class.java)
            groupSettingModel ?: return
            if(groupSettingModel.groupSettingId == currentGroupSettingID) {
                addRecruiterLocationMarker(groupSettingModel)
            }

        }

        private fun addRecruiterLocationMarker(groupSettingModel: GroupSettingModel) {
            val initLatLng = LatLng(groupSettingModel.recruiterLat, groupSettingModel.recruiterLng)
            val markerOptions = MarkerOptions()
            markerOptions.position(initLatLng)
            markerOptions.title("recruiter location")
            map.addMarker(markerOptions)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onCancelled(error: DatabaseError) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        deliveryBinding = ActivityDeliveryBinding.inflate(layoutInflater)
        val view = deliveryBinding.root
        setContentView(view)

        groupSettingDB = Firebase.database.reference.child(DBKey.DB_GROUPSETTING)

        setupGoogleMap()
        locationInit()

        deliveryBinding.clickButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Did you picked up your meal?")
                .setPositiveButton("Yes") { _: DialogInterface, _:Int -> finish()
                    currentGroupSettingID = null
                    Log.d("check public id", "$currentGroupSettingID")
                }
                .setNegativeButton("Not Yet") { _: DialogInterface, _:Int->}
                .show()

        }

    }

    private fun setupGoogleMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.showRecruiterLocation) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        setDefaultLocation()
    }

    private fun setDefaultLocation() {
        val hongik = LatLng(37.55169195608614, 126.92498046225892)
        map.moveCamera(CameraUpdateFactory.newLatLng(hongik))
    }

    private fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationCallback = MyLocationCallBack()

        locationRequest = LocationRequest()   // LocationRequest ????????? ?????? ?????? ?????? ?????? ????????? ???
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY   // GPS ??????
        locationRequest.interval = 10000   // 10???. ????????? ?????? ?????? ????????? ??? ?????? ?????? ????????? ????????????
        // ???????????? ??? ????????? ?????? ??????
        locationRequest.fastestInterval = 5000  // ????????? ??? ????????? ???????????? ?????? ?????? (????????? ?????? ?????????)
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            null)
    }

    inner class MyLocationCallBack: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation   // GPS??? ?????? ?????? ?????? Location ?????????
            // null??? ??? ?????? ??????

            location?.run {
                val latLng = LatLng(latitude, longitude)   // ??????, ??????
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))  // ????????? ??????


                //?????? ???????????? groupsetting??? ?????? recruiter??? ????????? ????????? ??????
                groupSettingDB.addChildEventListener(listener)

                Log.d("MapsActivity", "??????: $latitude, ??????: $longitude")     // ?????? ?????? ???
            }
        }
    }

    /*
    *
    *  ===== ?????? ??? ???????????? =====
    *
    *  */
    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit) {   // ???????????????, ???????????? ??????
        // ??? ?????? ????????? ??????

        if (ContextCompat.checkSelfPermission(this,                  // ????????? ?????? ??????
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {       // ?????? ?????? ????????? ?????? ??????

                cancel()

            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION)
            }
        } else {                                                    // ????????? ?????? ??????
            ok()
        }
    }

    private  fun showPermissionInfoDialog() {
        alert("?????? ????????? ???????????? ?????? ????????? ???????????????", "????????? ????????? ??????") {
            yesButton {
                ActivityCompat.requestPermissions(this@DeliveryActivity,  // ??? ????????????: Context ?????? Activity
                    // this: DialogInterface ??????
                    // this@MapsActivity??? ??????????????? ??????????????? ????????? ??????
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION)
            }
            noButton {  }
        }.show()
    }

    // ?????? ?????? ?????? ??????
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    addLocationListener()
                } else {
                    toast("????????? ?????? ???")
                }
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // ?????? ??????
        permissionCheck(
            cancel = { showPermissionInfoDialog() },   // ?????? ?????? ?????????
            ok = { addLocationListener()}      // ???   ??????????????? ?????? ????????? ??????
        )
    }

    override fun onPause() {
        super.onPause()

        removeLocationListener()    // ?????? ???????????? ?????? ????????? ?????? ?????? ?????? ??????
    }

    private fun removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}