package com.zing.zalo.hsapp.framework.util.executor

interface TaskExecutorRepeater {
    fun start(interval: Long, task: () -> Unit)
    fun stop()
}

object TaskExecutorRepeaterFactory {

    private lateinit var executor: TaskExecutorRepeater

    fun getTaskExecutorRepeater(): TaskExecutorRepeater {
        if (!::executor.isInitialized) {
            executor = TaskExecutorRepeaterByCdTimer()
        }
        return executor
    }
}