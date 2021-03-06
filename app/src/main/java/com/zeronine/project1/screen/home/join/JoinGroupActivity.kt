package com.zeronine.project1.screen.home.join

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.zeronine.project1.R
import com.zeronine.project1.data.DB.DBKey.Companion.DB_GROUPSETTING
import com.zeronine.project1.data.DB.DBKey.Companion.DB_GROUPSETTINGMEMBER
import com.zeronine.project1.data.DB.DBKey.Companion.DB_USERS
import com.zeronine.project1.databinding.ActivityJoinBinding
import com.zeronine.project1.screen.home.make.WaitingMemberActivity
import com.zeronine.project1.screen.home.make.currentGroupSettingID
import com.zeronine.project1.widget.model.GroupSettingModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class JoinGroupActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener {

    private lateinit var joinBinding: ActivityJoinBinding
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var groupSettingDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private lateinit var memberInfoDB: DatabaseReference
//    private lateinit var groupSettingAdapter: GroupSettingAdapter

    private var memberNum: Long = 0

    private val groupSettingList = mutableListOf<GroupSettingModel>()
    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val groupSettingModel = snapshot.getValue(GroupSettingModel::class.java)
            groupSettingModel ?: return
            if (groupSettingModel.recruiting == 1) {  // ??? ???????????? ????????? ?????????(1)????????? groupSettingList ??? ????????????
                groupSettingList.add(groupSettingModel)
                addGroupSettingMarkerOnMap(groupSettingModel)
                Log.d("CHECK THIS!!", "${groupSettingModel}")
            }
            //groupSettingAdapter.submitList(groupSettingList)
//            groupSettingAdapter.submitList(mutableListOf<GroupSettingModel>().apply {
//                add(GroupSettingModel("45678", "aa", "dd", "dd", "", "", "", "", ""))
//            })
        }

        private fun addGroupSettingMarkerOnMap(groupSettingModel: GroupSettingModel) {

            val initLatLng = LatLng(groupSettingModel.recruiterLat, groupSettingModel.recruiterLng)
            val markerOptions = MarkerOptions()
            markerOptions.position(initLatLng)
            markerOptions.title(groupSettingModel.foodCategory)
            markerOptions.snippet("people: " + groupSettingModel.totalPeople + " time: " + groupSettingModel.waitingTime)
            val marker = map.addMarker(markerOptions)
            marker!!.tag = groupSettingModel.groupSettingId
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

        joinBinding = ActivityJoinBinding.inflate(layoutInflater)
        val view = joinBinding.root
        setContentView(view)

        groupSettingDB = Firebase.database.reference.child(DB_GROUPSETTING)
        memberInfoDB = Firebase.database.reference.child(DB_GROUPSETTINGMEMBER)
        userDB = Firebase.database.reference.child(DB_USERS)
//        groupSettingAdapter = GroupSettingAdapter()

//        joinBinding.recyclerView.layoutManager = LinearLayoutManager(this)
//        joinBinding.recyclerView.adapter = groupSettingAdapter

        setupGoogleMap()
        locationInit()
        groupSettingDB.addChildEventListener(listener)
    }



    @Override
    public override fun onMarkerClick(marker: Marker): Boolean {
        marker.showInfoWindow()

        Log.d("MarkerClicked", "marker tag : ${marker.tag}")
        val markerGroupSettingId = marker.tag!!.toString()

        val foodCategoryDB =
            markerGroupSettingId?.let { groupSettingDB.child(it).child("foodCategory") }
        val peopleDB = markerGroupSettingId?.let { groupSettingDB.child(it).child("totalPeople") }
        val timeDB = markerGroupSettingId?.let { groupSettingDB.child(it).child("waitingTime") }
        foodCategoryDB!!.get().addOnSuccessListener {
            joinBinding.showMarkerFoodCategory.text = "??? food category : ${it.value}"
        }
        peopleDB!!.get().addOnSuccessListener {
            joinBinding.showMarkerPeopleStatus.text = "??? total people : ${it.value}"
        }
        timeDB!!.get().addOnSuccessListener {
            joinBinding.showMarkerTimeStatus.text = "??? waiting time : ${it.value}"
        }

        val groupSettingId = marker.tag.toString()
        Log.d("Check marker tag", "${groupSettingId}")

        return true
    }

    @Override
    public override fun onInfoWindowClick(marker: Marker) {
        val groupSettingId = marker.tag.toString()
//        Log.d("Check marker tag", "${groupSettingId}")
        val currentUserId = Firebase.auth.currentUser?.uid.orEmpty()
        val myMemberInfoDB = memberInfoDB.child(groupSettingId)
        val currentGroupSettingDB = groupSettingDB.child(groupSettingId)


        //?????? ????????? ???????????? ????????? .recruiting ????????? ???????????? ???????????????????????? ????????? ??? ????????? ??????.
        lateinit var name : String
        currentGroupSettingDB.child("recruiting").get().addOnSuccessListener {
            Log.d("Check recruiting", "${it.value}")
            if (it.value.toString() == "1") { //  ??????????????? ?????? ???????????????
                currentGroupSettingID = groupSettingId
                addUserToMemberInfoDB(marker)
                startActivity(Intent(this, WaitingMemberActivity::class.java))
                finish()
            }
            else {
                Log.d("Check recruiting", "${it.value}")
                Toast.makeText(this, "??? ??????????????? ????????? ?????????????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
            }
        }

//        val myMemberNum = (memberNum + 1).toInt()
//        Log.d("Check_memberNum", "${memberNum}")

//        val currentMemberNum = memberInfoDB.

//        if (totalPeople == myMemberNum) {   //???????????? ??????????????? ????????? recruiting ????????? ?????????????????? ????????????
//            currentGroupSettingDB.chil d("recruiting").setValue(2)
//            /*
//        recruiting
//        0 : ??????????????? ?????????
//        1 : ???????????? ?????????
//        2 : ???????????? ????????? (????????????)
//        3 : ???????????? ?????? (????????????)
//        4 : ???????????? ??????
//         */
//        }

    }

    private fun addUserToMemberInfoDB(marker: Marker) {
        val groupSettingId = marker.tag.toString()
//        Log.d("Check marker tag", "${groupSettingId}")
        val currentUserId = Firebase.auth.currentUser?.uid.orEmpty()
        val myMemberInfoDB = memberInfoDB.child(groupSettingId)
        val currentGroupSettingDB = groupSettingDB.child(groupSettingId)
        //lateinit var name : String

        val myInfo = mutableMapOf<String, Any>()
        myInfo["MemberId"] = currentUserId
        userDB.child(currentUserId).child("name").get().addOnSuccessListener {
            myInfo["MemberName"] = it.value.toString()
            Log.d("getName", "${it.value}")
            myMemberInfoDB.child(currentUserId).updateChildren(myInfo)
        }


    }


    private fun setupGoogleMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        setDefaultLocation()
        //map.setOnInfoWindowClickListener(this)
        map.setOnMarkerClickListener(this)
        map.setOnInfoWindowClickListener(this)
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
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    inner class MyLocationCallBack : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation   // GPS??? ?????? ?????? ?????? Location ?????????
            // null??? ??? ?????? ??????

            location?.run {
                val latLng = LatLng(latitude, longitude)   // ??????, ??????
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))  // ????????? ??????

                Log.d("MapsActivity", "??????: $latitude, ??????: $longitude")     // ?????? ?????? ???
            }
        }
    }


    /* (3?????? ??????)
    *
    *  ===== ?????? ??? ???????????? =====
    *
    *  */
    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit) {   // ???????????????, ???????????? ??????
        // ??? ?????? ????????? ??????

        if (ContextCompat.checkSelfPermission(
                this,                  // ????????? ?????? ??????
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {       // ?????? ?????? ????????? ?????? ??????

                cancel()

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION
                )
            }
        } else {                                                    // ????????? ?????? ??????
            ok()
        }
    }

    private fun showPermissionInfoDialog() {
        alert("?????? ????????? ???????????? ?????? ????????? ???????????????", "????????? ????????? ??????") {
            yesButton {
                ActivityCompat.requestPermissions(
                    this@JoinGroupActivity,  // ??? ????????????: Context ?????? Activity
                    // this: DialogInterface ??????
                    // this@JoinGroupActivity??? ??????????????? ??????????????? ????????? ??????
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION
                )
            }
            noButton { }
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
            ok = { addLocationListener() }      // ???   ??????????????? ?????? ????????? ??????
        )

//        TODO("???????????? ??????????????? ?????? ????????? ????????????, ??? ????????????")
    }

    override fun onPause() {
        super.onPause()

        removeLocationListener()    // ?????? ???????????? ?????? ????????? ?????? ?????? ?????? ??????
    }

    private fun removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }


}