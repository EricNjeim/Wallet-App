package com.example.eric_summer2023


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService


class ReminderService  : BroadcastReceiver() {

        private var channelID = "ericchannelID"
        private var notificationManager:NotificationManager?=null
    private var context: Context? = null



    override fun onReceive(context: Context?, intent: Intent) {
        if (context == null) {
            return
        }

        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            // Reset all alarms
        } else {
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(channelID, "Demo", "ddddd")
            displaynotification()
        }
    }

    private fun createNotificationChannel(id: String, channelName: String, channelDescription: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance=NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, channelName, importance).apply {

            }
            channel.description = channelDescription

            notificationManager?.createNotificationChannel(channel)
        }
    }
    private fun displaynotification(){
        val notificationid=45
        val notification=NotificationCompat.Builder(context!!,channelID)
            .setContentTitle("Bills Due")
            .setContentText("You have a bill to pay today!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notificationManager?.notify(notificationid,notification)
    }
}
