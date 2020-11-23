package com.zing.zalo.hsapp.framework.alarm.clock

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.work.*
import com.zing.zalo.hsapp.framework.alarm.service.AlarmService
import com.zing.zalo.hsapp.framework.util.AppConstants
import com.zing.zalo.usecase.sleep.DateTimeConvert.convertDateHM
import io.karn.notify.Notify
import java.util.concurrent.TimeUnit

/**
 * Implement AlarmClock by WorkManager
 */
class AlarmClockByWorkerManager(appContext: Context) : AlarmClock {

    private val workManager = WorkManager.getInstance(appContext)
    private var timeInMillis: Long = 0

    override fun setAlarmAt(timeInMillis: Long) {
        this.timeInMillis = timeInMillis
        val timeData: Data = workDataOf("AWAKE_TIME" to convertDateHM(timeInMillis))
        val myWorkRequest = OneTimeWorkRequestBuilder<AlarmWorker>()
            .setInitialDelay(timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setInputData(timeData)
            .build()

        workManager.enqueueUniqueWork(
            AppConstants.APP_NAME,
            ExistingWorkPolicy.REPLACE,
            myWorkRequest
        )
    }

    override fun cancelAlarm() {
        workManager.cancelUniqueWork(AppConstants.APP_NAME)
    }

    class AlarmWorker(appContext: Context, workerParams: WorkerParameters):
        Worker(appContext, workerParams) {

        override fun doWork(): Result {
            val serviceIntent = Intent(applicationContext, AlarmService::class.java)
            val timeStr = inputData.getString("AWAKE_TIME")
            serviceIntent.putExtra("AWAKE_TIME", timeStr)
            ContextCompat.startForegroundService(applicationContext, serviceIntent)

            // Indicate whether the work finished successfully with the Result
            return Result.success()
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


}