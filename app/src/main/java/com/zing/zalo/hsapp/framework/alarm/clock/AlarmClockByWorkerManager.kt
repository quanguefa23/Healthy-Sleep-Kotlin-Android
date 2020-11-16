package com.zing.zalo.hsapp.framework.alarm.clock

import android.content.Context
import androidx.work.*
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.framework.util.AppConstants
import io.karn.notify.Notify
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Implement AlarmClock by WorkManager
 */
class AlarmClockByWorkerManager(appContext: Context) : AlarmClock {

    private val workManager = WorkManager.getInstance(appContext)
    private var timeInMillis: Long = 0

    override fun setAlarmAt(timeInMillis: Long) {
        this.timeInMillis = timeInMillis
        val myWorkRequest = OneTimeWorkRequestBuilder<AlarmWorker>()
            .setInitialDelay(timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
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
            Timber.d("end")
            buildNotification(applicationContext, applicationContext.getString(R.string.wakeup), "temp")
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