package com.zing.zalo.hsapp.framework.alarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.zing.zalo.hsapp.framework.alarm.service.AlarmService
import com.zing.zalo.hsapp.framework.alarm.service.AlarmService.Companion.ACTION_CANCEL

/**
 * Receiver to cancel alarm service
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        if (action == ACTION_CANCEL) {
            cancelAction(context)
        }

        // Close system dialogs
        val cancelIntent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        context.sendBroadcast(cancelIntent)
    }

    private fun cancelAction(context: Context) {
        // Stop pin code service
        val serviceIntent = Intent(context, AlarmService::class.java)
        context.stopService(serviceIntent)
    }
}