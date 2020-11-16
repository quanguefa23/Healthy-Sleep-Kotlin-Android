package com.zing.zalo.hsapp.framework.alarm.clock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.zing.zalo.hsapp.framework.alarm.receiver.AlarmReceiver
import com.zing.zalo.hsapp.framework.util.AppConstants
import com.zing.zalo.hsapp.framework.alarm.RandomNumber

/**
 * Implement AlarmClock by AlarmManager (deprecated)
 */
class AlarmClockByAlarmManager(private val context: Context) : AlarmClock {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    override fun setAlarmAt(timeInMillis: Long) {
        setAlarm(
            timeInMillis,
            getPendingIntent(
                getIntent().apply {
                    action = AppConstants.ACTION_SET_EXACT
                    putExtra(AppConstants.EXTRA_EXACT_ALARM_TIME, timeInMillis)
                }
            )
        )
    }

    override fun cancelAlarm() {
        TODO("Not yet implemented")
    }

    private fun getPendingIntent(intent: Intent) =
        PendingIntent.getBroadcast(
            context,
            RandomNumber.getRandomInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            } else {
                setExact(
                    AlarmManager.RTC_WAKEUP,
                    timeInMillis,
                    pendingIntent
                )
            }
        }
    }

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

}