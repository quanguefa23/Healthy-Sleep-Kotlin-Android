package com.zing.zalo.hsapp.framework.util.executor

import android.os.CountDownTimer

/**
 * Use main thread, only do tiny tasks
 */
class TaskExecutorRepeaterByCdTimer: TaskExecutorRepeater {

    companion object {
        const val MAX_NUMBER: Long = 1000000
    }

    private var timer: CountDownTimer? = null

    override fun start(interval: Long, task: () -> Unit) {
        timer = object : CountDownTimer(MAX_NUMBER, interval) {
            override fun onTick(millisUntilFinished: Long) {
                task()
            }

            override fun onFinish() {
                this.start()
            }
        }.start()
    }

    override fun stop() {
        timer?.cancel()
        timer = null
    }
}