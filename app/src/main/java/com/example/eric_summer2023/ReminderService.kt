package com.example.eric_summer2023
    import android.app.PendingIntent
    import android.content.BroadcastReceiver
    import android.content.Context
    import android.content.Intent


class ReminderService  : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent) {



        if ((Intent.ACTION_BOOT_COMPLETED)==(p1.action)){
            // reset all alarms
        }
        else{
            // perform your scheduled task here (eg. send alarm notification)
        }
    }
}