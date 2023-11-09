package com.example.eric_summer2023


    import android.app.Service
    import android.content.Intent
    import android.media.MediaPlayer
    import android.os.IBinder
    import android.provider.Settings

    class Service : Service() {

        override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {


            return START_STICKY
        }


        override fun onDestroy() {
            super.onDestroy()


        }

        override fun onBind(intent: Intent): IBinder? {
            return null
        }
    
}