package com.example.autonavimapsample

import android.app.Application
import android.content.Intent
import android.os.Build
import android.util.Log
import com.amap.api.location.AMapLocationClient

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AMapLocationClient.updatePrivacyAgree(this, true)
        AMapLocationClient.updatePrivacyShow(this, true, true)

        Log.d("GJ","SampleApplication oncreate")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, LocationService::class.java))
        } else {
            startService(Intent(this, LocationService::class.java))
        }
    }
}