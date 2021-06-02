package com.bangkit.scade.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
        private const val TIME_FORMAT = "HH:mm"
        private const val NOTIF_ID = 1
        private const val ID_ONETIME = 100
    }

    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context)
    }

    fun setOneTimeAlarm(context: Context) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        val message = "Don't Forget to Check Up"

        val currentTime = Calendar.getInstance().timeInMillis
        val targetTime = currentTime.plus(604800000)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_ONETIME, intent, 0)
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetTime, pendingIntent)
    }

    fun showNotification(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage("com.bagkit.scade")
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentIntent(pendingIntent)
            .setContentTitle("Check Up Reminder")
            .setContentText("Don't Forget to Consult Your Last Week Check Up")
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)

            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()

        notificationManagerCompat.notify(NOTIF_ID, notification)
    }
}