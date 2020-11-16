package com.zing.zalo.hsapp.framework.alarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.framework.util.AppConstants
import com.zing.zalo.usecase.sleep.DateTimeConvert
import io.karn.notify.Notify
import timber.log.Timber

/**
 * receiver to push a notification
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(AppConstants.EXTRA_EXACT_ALARM_TIME, 0L)
        when (intent.action) {
            AppConstants.ACTION_SET_EXACT -> {
                Timber.i("notiiii")
                Toast.makeText(context, "báo thức reo kìa hihi", Toast.LENGTH_LONG).show()
                buildNotification(context,context.getString(R.string.wakeup),
                    DateTimeConvert.convertDateHM(timeInMillis))
            }
        }
    }

    private fun buildNotification(context: Context, title: String, message: String) {
        Notify
            .with(context)
            .content {
                this.title = title
                text = "$message rồi, dậy đi bạn ơi!!!"
            }
            .show()
    }
}