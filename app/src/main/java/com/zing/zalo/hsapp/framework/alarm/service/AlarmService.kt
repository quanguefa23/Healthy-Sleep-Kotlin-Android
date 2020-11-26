package com.zing.zalo.hsapp.framework.alarm.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.framework.alarm.MediaFactory
import com.zing.zalo.hsapp.framework.alarm.clock.AWAKE_TIME_KEY
import com.zing.zalo.hsapp.framework.alarm.clock.MEDIA_OPTION_KEY
import com.zing.zalo.hsapp.framework.alarm.receiver.AlarmReceiver
import com.zing.zalo.hsapp.presentation.view.activity.MainActivity
import timber.log.Timber

/**
 * A Foreground Service to keep alarm alive until user click stop
 */
class AlarmService : Service() {

    companion object {
        private const val CODE_CHANNEL_ID = "Alarm-Healthy-Sleep"
        private const val CANCEL_REQUEST_CODE = 124
        private const val ONGOING_NOTIFICATION_ID = 232
        const val ACTION_CANCEL = "cancel_action"
    }

    private val handler = Handler(Looper.getMainLooper())
    private val stopServiceCallback = Runnable { stopSelf() }
    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Alarm"
            val description = "Alarm Notification For Healthy Sleep"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CODE_CHANNEL_ID, name, importance)
            channel.description = description

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // start ring tone
        val mediaOpt = intent.getIntExtra(MEDIA_OPTION_KEY, 0)
        mediaPlayer = MediaFactory.createLoopingRingTone(this, mediaOpt)
        mediaPlayer?.start()

        // Create an explicit intent for tap action
        val contentIntent = Intent(this, MainActivity::class.java)
        contentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val contentPendingIntent = PendingIntent.getActivity(
            this, 0, contentIntent, 0
        )

        // Create intent for cancel action
        val cancelIntent = Intent(this, AlarmReceiver::class.java)
        cancelIntent.action = ACTION_CANCEL
        val cancelPendingIntent = PendingIntent.getBroadcast(
            this,
            CANCEL_REQUEST_CODE, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Create notification
        val timeStr: String = intent.getStringExtra(AWAKE_TIME_KEY) ?: "time null"
        val builder = createBuilder(contentPendingIntent, timeStr)
        // Add action to builder
        builder.addAction(R.drawable.ic_app_icon, getString(R.string.stop_alarm), cancelPendingIntent)

        startForeground(ONGOING_NOTIFICATION_ID, builder.build())

        // auto stop alarm after 5 minutes (300s)
        handler.postDelayed( stopServiceCallback, 300 * 1000)

        return START_REDELIVER_INTENT
    }

    private fun createBuilder(
        contentPendingIntent: PendingIntent,
        contentText: String
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, CODE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app_icon)
            .setContentTitle(getString(R.string.alarm))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentPendingIntent)
            .setContentText(contentText)
    }

    override fun onDestroy() {
        Timber.i("destroy AlarmService")
        mediaPlayer?.run {
            stop()
            release()
        }
        handler.removeCallbacks(stopServiceCallback)
        super.onDestroy()
    }
}