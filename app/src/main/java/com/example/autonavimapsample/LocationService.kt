package com.example.autonavimapsample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.alibaba.idst.nui.BuildConfig
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

class LocationService : Service() {

    private val TAG:String = "LocationService"

    private lateinit var option: AMapLocationClientOption
    private lateinit var client: AMapLocationClient

    override fun onCreate() {
        super.onCreate()

        initNotification()
        initAMapService()

        Log.d(TAG, "LocationService start ---")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    private fun initAMapService() {
        client = AMapLocationClient(this)

        option = AMapLocationClientOption()
        option.isNeedAddress = true
        option.interval = 2000

        client.setLocationOption(option)
        client.setLocationListener(locationListener)
        client.startLocation()
    }

    private fun initNotification() {
        val channelName = "埋点上传"
        val channelId = BuildConfig.APPLICATION_ID

        // 发送通知，把service置于前台
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // 从Android 8.0开始，需要注册通知通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Using location")
            .setContentText("服务正在运行，请勿关闭")
            .setAutoCancel(false)
            .setOngoing(true)
            .build()

        // 注意第一个参数不能为0
        startForeground(1, notification)
    }

    private var locationListener = object : AMapLocationListener {
        override fun onLocationChanged(amapLocation: AMapLocation?) {
            if(amapLocation?.errorCode==0){
                Log.d(TAG, "detail:${amapLocation?.address} ${amapLocation?.latitude}:${amapLocation?.longitude}")
            }else{
                Log.d(TAG, "errorCode:${amapLocation?.errorCode}")
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        client.let {
            client.stopLocation()
            client.onDestroy()
        }

        Log.d(TAG, "LocationService destroy ---")
    }
}