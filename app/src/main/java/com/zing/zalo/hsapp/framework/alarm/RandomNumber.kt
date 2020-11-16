package com.zing.zalo.hsapp.framework.alarm

import java.util.concurrent.atomic.AtomicInteger

/**
 * Get random integer
 */
object RandomNumber {
    private val seed = AtomicInteger()
    fun getRandomInt() = seed.getAndIncrement() + System.currentTimeMillis().toInt()
}