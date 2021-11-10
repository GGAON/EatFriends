package com.zeronine.project1.screen.home


import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.zeronine.project1.R
import com.zeronine.project1.databinding.ActivitySettingBinding
import com.zeronine.project1.databinding.DialogGroupsettingBinding
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.util.*

//공동구매 그룹세팅 변수 3가지
public var foodCategory : String? = null
public var totalPeople : Int? = 2
public var waitingTime : Int? = 5

//공동구매 모집자의 현재 위치 변수 2가지
public var recruiterLat : Double? = 37.55169195608614
public var recruiterLng : Double? = 126.92498046225892

//뒤로가기 버튼을 누르기 위한 시간 세팅
private var backWait: Long = 0

class MakeGroupActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var activitySettingBinding: ActivitySettingBinding
    private lateinit var settingBinding: DialogGroupsettingBinding
    //private val totalPeople = 0

    //위치 받아오기
    private lateinit var map:GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentMarker : Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySettingBinding = ActivitySettingBinding.inflate(layoutInflater)
        val view = activitySettingBinding.root
        setContentView(view)

        settingBinding = DialogGroupsettingBinding.inflate(layoutInflater)

        setupGoogleMap()
        locationInit()

        initNumberPicker()
        foodButtonMenuListener()
        numberPickerListener()


        activitySettingBinding.startToMakeGroupButton.setOnClickListener {
            if(foodCategory == null) {
                Toast.makeText(this, "음식카테고리를 골라주세요!!", Toast.LENGTH_SHORT).show()
            }
            else {
                GroupSettingDialogFragment().show(supportFragmentManager, "GroupSettingDialogFragment")
                finish()
            }
        }

    }

    /*
    음식메뉴 고르기
    버튼 1개 누른 후 다른 메뉴 버튼 누르면 모두 안눌림으로 변경
     */
    private fun foodButtonMenuListener() {
        activitySettingBinding.btnKorean.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activitySettingBinding.btnChicken.isChecked = false
                activitySettingBinding.btnPizza.isChecked = false
                activitySettingBinding.btnSchoolFood.isChecked = false
                activitySettingBinding.btnFastFood.isChecked = false
                activitySettingBinding.btnCafeDessert.isChecked = false
                activitySettingBinding.btnChineseFood.isChecked = false
                activitySettingBinding.btnJapanFood.isChecked = false
                activitySettingBinding.btnAsianFood.isChecked = false
                foodCategory = "Korean"
                Log.d("food clicked", "Korean is clicked")
            }
            else {
                foodCategory = null
            }
        }

        activitySettingBinding.btnChicken.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activitySettingBinding.btnKorean.isChecked = false
                activitySettingBinding.btnPizza.isChecked = false
                activitySettingBinding.btnSchoolFood.isChecked = false
                activitySettingBinding.btnFastFood.isChecked = false
                activitySettingBinding.btnCafeDessert.isChecked = false
                activitySettingBinding.btnChineseFood.isChecked = false
                activitySettingBinding.btnJapanFood.isChecked = false
                activitySettingBinding.btnAsianFood.isChecked = false
                foodCategory = "Chicken"
                Log.d("food clicked", "Chicken is clicked")
            }
            else {
                foodCategory = null
            }
        }

        activitySettingBinding.btnPizza.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activitySettingBinding.btnKorean.isChecked = false
                activitySettingBinding.btnChicken.isChecked = false
                activitySettingBinding.btnSchoolFood.isChecked = false
                activitySettingBinding.btnFastFood.isChecked = false
                activitySettingBinding.btnCafeDessert.isChecked = false
                activitySettingBinding.btnChineseFood.isChecked = false
                activitySettingBinding.btnJapanFood.isChecked = false
                activitySettingBinding.btnAsianFood.isChecked = false
                foodCategory = "Pizza"
                Log.d("food clicked", "Pizza is clicked")
            }
            else {
                foodCategory = null
            }
        }

        activitySettingBinding.btnSchoolFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activitySettingBinding.btnKorean.isChecked = false
                activitySettingBinding.btnChicken.isChecked = false
                activitySettingBinding.btnPizza.isChecked = false
                activitySettingBinding.btnFastFood.isChecked = false
                activitySettingBinding.btnCafeDessert.isChecked = false
                activitySettingBinding.btnChineseFood.isChecked = false
                activitySettingBinding.btnJapanFood.isChecked = false
                activitySettingBinding.btnAsianFood.isChecked = false
                foodCategory = "School Food"
                Log.d("food clicked", "School Food is clicked")
            }
            else {
                foodCategory = null
            }
        }

        activitySettingBinding.btnFastFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activitySettingBinding.btnKorean.isChecked = false
                activitySettingBinding.btnChicken.isChecked = false
                activitySettingBinding.btnPizza.isChecked = false
                activitySettingBinding.btnSchoolFood.isChecked = false
                activitySettingBinding.btnCafeDessert.isChecked = false
                activitySettingBinding.btnChineseFood.isChecked = false
                activitySettingBinding.btnJapanFood.isChecked = false
                activitySettingBinding.btnAsianFood.isChecked = false
                foodCategory = "Fast Food"
                Log.d("food clicked", "Fast Food is clicked")
            }
            else {
                foodCategory = null
            }
        }

        activitySettingBinding.btnCafeDessert.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activitySettingBinding.btnKorean.isChecked = false
                activitySettingBinding.btnChicken.isChecked = false
                activitySettingBinding.btnPizza.isChecked = false
                activitySettingBinding.btnSchoolFood.isChecked = false
                activitySettingBinding.btnFastFood.isChecked = false
                activitySettingBinding.btnChineseFood.isChecked = false
                activitySettingBinding.btnJapanFood.isChecked = false
                activitySettingBinding.btnAsianFood.isChecked = false
                foodCategory = "Cafe/Dessert"
                Log.d("food clicked", "Cafe/Dessert is clicked")
            }
            else {
                foodCategory = null
            }
        }

        activitySettingBinding.btnChineseFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activitySettingBinding.btnKorean.isChecked = false
                activitySettingBinding.btnChicken.isChecked = false
                activitySettingBinding.btnPizza.isChecked = false
                activitySettingBinding.btnSchoolFood.isChecked = false
                activitySettingBinding.btnFastFood.isChecked = false
                activitySettingBinding.btnCafeDessert.isChecked = false
                activitySettingBinding.btnJapanFood.isChecked = false
                activitySettingBinding.btnAsianFood.isChecked = false
                foodCategory = "Chinese Food"
                Log.d("food clicked", "Chinese Food is clicked")
            }
            else {
                foodCategory = null
            }
        }

        activitySettingBinding.btnJapanFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activitySettingBinding.btnKorean.isChecked = false
                activitySettingBinding.btnChicken.isChecked = false
                activitySettingBinding.btnPizza.isChecked = false
                activitySettingBinding.btnSchoolFood.isChecked = false
                activitySettingBinding.btnFastFood.isChecked = false
                activitySettingBinding.btnCafeDessert.isChecked = false
                activitySettingBinding.btnChineseFood.isChecked = false
                activitySettingBinding.btnAsianFood.isChecked = false
                foodCategory = "Japan Food"
                Log.d("food clicked", "Japan Food is clicked")
            }
            else {
                foodCategory = null
            }
        }

        activitySettingBinding.btnAsianFood.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                activitySettingBinding.btnKorean.isChecked = false
                activitySettingBinding.btnChicken.isChecked = false
                activitySettingBinding.btnPizza.isChecked = false
                activitySettingBinding.btnSchoolFood.isChecked = false
                activitySettingBinding.btnFastFood.isChecked = false
                activitySettingBinding.btnCafeDessert.isChecked = false
                activitySettingBinding.btnChineseFood.isChecked = false
                activitySettingBinding.btnJapanFood.isChecked = false
                foodCategory = "Asian Food"
                Log.d("food clicked", "Asian Food is clicked")
            }
            else {
                foodCategory = null
            }
        }
    }


    private fun initNumberPicker() {
        activitySettingBinding.numberPickerFriends.minValue = 1
        activitySettingBinding.numberPickerFriends.maxValue = 3
        activitySettingBinding.numberPickerFriends.wrapSelectorWheel = true
        activitySettingBinding.numberPickerFriends.value = 1
        activitySettingBinding.numberPickerWaitTime.minValue = 5
        activitySettingBinding.numberPickerWaitTime.maxValue = 59
        activitySettingBinding.numberPickerWaitTime.wrapSelectorWheel = true
        val firstTotalPeople = activitySettingBinding.numberPickerFriends.value + 1
//        foodBinding.showTotalPeopleNow.setText("Total number of people including you is ${firstTotalPeople}")
        activitySettingBinding.showTotalPeopleNow.text =
            "Total number of people including you is ${firstTotalPeople}"
//        foodBinding.totalPeopleWarningText.setText("(※ Because of COVID-19, the maximum number of people on group is 4. )")
        activitySettingBinding.totalPeopleWarningText.text =
            "(※ Because of COVID-19, the maximum number of people on group is 4. )"
    }

    private fun numberPickerListener() {
        activitySettingBinding.numberPickerFriends.setOnValueChangedListener { _, oldVal, newVal ->
            Log.d("numberPicker_friends", "oldVal : ${oldVal}, newVal : $newVal ")
            totalPeople = newVal.toInt() + 1
            Log.d("checkVar", "${totalPeople}")
            activitySettingBinding.showTotalPeopleNow.text = "Total number of people including you is ${totalPeople}"
            activitySettingBinding.totalPeopleWarningText.text = "(※ The maximum number of people on group is 4. )"
        }
        activitySettingBinding.numberPickerWaitTime.setOnValueChangedListener { _, oldVal, newVal ->
            waitingTime = newVal.toInt()
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Are you canceling the group purchase setting?")
            .setPositiveButton("Cancel") {_:DialogInterface, _:Int -> finish()}
            .setNegativeButton("Make group") {_:DialogInterface, _:Int->}
            .show()

//        if (System.currentTimeMillis() - backWait >= 2000) {
//            backWait = System.currentTimeMillis()
//            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 공동구매 그룹 세팅이 종료됩니다.", Toast.LENGTH_SHORT).show()
//        } else {
//
//            super.onBackPressed()
//            foodCategory = null
//            totalPeople = 2
//            waitingTime = 5
//            finish()
//        }
    }

    private fun setupGoogleMap() {
        val groupSettingMapFragment =
            supportFragmentManager.findFragmentById(R.id.groupSettingMapFragment) as SupportMapFragment
        groupSettingMapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        setDefaultLocation()
    }


    private fun setDefaultLocation(){

        val hongik = LatLng(37.55169195608614, 126.92498046225892)
        map.moveCamera(CameraUpdateFactory.newLatLng(hongik))

    }

    private fun locationInit(){
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationCallback = MyLocationCallBack()

        locationRequest = LocationRequest()   // LocationRequest 객체로 위치 정보 요청 세부 설정을 함
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY   // GPS 우선
        locationRequest.interval = 10000   // 10초. 상황에 따라 다른 앱에서 더 빨리 위치 정보를 요청하면
        // 자동으로 더 짧아질 수도 있음
        locationRequest.fastestInterval = 5000  // 이보다 더 빈번히 업데이트 하지 않음 (고정된 최소 인터벌)
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

            val location = locationResult?.lastLocation   // GPS가 꺼져 있을 경우 Location 객체가
            // null이 될 수도 있음

            location?.run {
                val latLng = LatLng(latitude, longitude)   // 위도, 경도
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))  // 카메라 이동



                //현재위치에 마커 생성하고 이동
                setCurrentLocation(location)
                Log.d("MapsActivity", "위도: $latitude, 경도: $longitude")     // 로그 확인 용
            }
        }
    }




    private fun setCurrentLocation(location: Location) {
        if(currentMarker != null) currentMarker!!.remove()
        val currentLatLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(currentLatLng)
        markerOptions.draggable(true)
        currentMarker = map.addMarker(markerOptions)
        recruiterLat = location.latitude
        recruiterLng = location.longitude
    }


    /*
    *
    *  ===== 실행 중 권한요청 =====
    *
    *  */
    private val REQUEST_ACCESS_FINE_LOCATION = 1000

    private fun permissionCheck(cancel: () -> Unit, ok: () -> Unit) {   // 전달인자도, 리턴값도 없는
        // 두 개의 함수를 받음

        if (ContextCompat.checkSelfPermission(this,                  // 권한이 없는 경우
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {       // 권한 거부 이력이 있는 경우

                cancel()

            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION)
            }
        } else {                                                    // 권한이 있는 경우
            ok()
        }
    }

    private  fun showPermissionInfoDialog() {
        alert("위치 정보를 얻으려면 위치 권한이 필요합니다", "권한이 필요한 이유") {
            yesButton {
                ActivityCompat.requestPermissions(this@MakeGroupActivity,  // 첫 전달인자: Context 또는 Activity
                    // this: DialogInterface 객체
                    // this@MapsActivity는 액티비티를 명시적으로 가리킨 것임
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_ACCESS_FINE_LOCATION)
            }
            noButton {  }
        }.show()
    }

    // 권한 요청 결과 처리
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
                    toast("권한이 거부 됨")
                }
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // 권한 요청
        permissionCheck(
            cancel = { showPermissionInfoDialog() },   // 권한 필요 안내창
            ok = { addLocationListener()}      // ③   주기적으로 현재 위치를 요청
        )
    }

    override fun onPause() {
        super.onPause()

        removeLocationListener()    // 앱이 동작하지 않을 때에는 위치 정보 요청 제거
    }

    private fun removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}

