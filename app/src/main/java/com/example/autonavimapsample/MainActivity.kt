package com.example.autonavimapsample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amap.api.maps.MapView
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.maps.model.MyLocationStyle.*
import com.amap.api.maps.model.MyLocationStyleCreator
import java.security.Permissions

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        mapView = findViewById(R.id.map)
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);

        showCurrentPoint()

        checkPermission()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                listOf(Manifest.permission.ACCESS_FINE_LOCATION).toTypedArray(),1)
        }
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    fun showCurrentPoint() {
        var myLocationStyle=MyLocationStyle()

        myLocationStyle.myLocationType(LOCATION_TYPE_LOCATE)

        mapView.map.myLocationStyle = myLocationStyle
        mapView.map.isMyLocationEnabled = true

        mapView.map.uiSettings.isMyLocationButtonEnabled = true
    }

}