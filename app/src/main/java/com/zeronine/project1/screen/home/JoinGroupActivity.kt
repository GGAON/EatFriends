package com.zeronine.project1.screen.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
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
import com.zeronine.project1.R
import com.zeronine.project1.databinding.ActivityJoinBinding
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class JoinGroupActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var joinBinding: ActivityJoinBinding
    private lateinit var map:GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        joinBinding = ActivityJoinBinding.inflate(layoutInflater)
        val view = joinBinding.root
        setContentView(view)

        setupGoogleMap()
        locationInit()
    }


    private fun setupGoogleMap() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

                Log.d("MapsActivity", "위도: $latitude, 경도: $longitude")     // 로그 확인 용
            }
        }
    }


    /* (3단계 코딩)
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
                ActivityCompat.requestPermissions(this@JoinGroupActivity,  // 첫 전달인자: Context 또는 Activity
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