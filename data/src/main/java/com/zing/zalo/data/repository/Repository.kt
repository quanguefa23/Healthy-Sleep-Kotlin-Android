package com.zing.zalo.data.repository

/**
 * Control data flow in your app, decide tactical between in memory, local and remote storage
 */
class Repository(private val localRepository: LocalRepository,
                 private val inMemoryRepository: InMemoryRepository) {

    fun getSleepTimeStringByCycles(option: Int): String
            = inMemoryRepository.getSleepTimeStringByCycles(option)

    fun saveSleepTimeOption(pos: Int) {
        inMemoryRepository.saveSleepTimeOption(pos)
        localRepository.saveSleepTimeOption(pos)
    }
    fun getSleepTimeOption(): Int =
        inMemoryRepository.getSleepTimeOption() ?: localRepository.getSleepTimeOption().also {
            inMemoryRepository.saveSleepTimeOption(it)
        }

    fun saveTypeOfFirstFragment(type: Int) {
        inMemoryRepository.saveTypeOfFirstFragment(type)
        localRepository.saveTypeOfFirstFragment(type)
    }
    fun getTypeOfFirstFragment(): Int =
        inMemoryRepository.getTypeOfFirstFragment() ?: localRepository.getTypeOfFirstFragment().also {
            inMemoryRepository.saveTypeOfFirstFragment(it)
        }

    fun saveWakeUpTime(wakeupTime: Long) {
        inMemoryRepository.saveWakeUpTime(wakeupTime)
        localRepository.saveWakeUpTime(wakeupTime)
    }
    fun getWakeUpTime(): Long =
        inMemoryRepository.getWakeUpTime() ?: localRepository.getWakeUpTime().also {
            inMemoryRepository.saveWakeUpTime(it)
        }

    fun saveAlarmClockOption(opt: Int) {
        inMemoryRepository.saveAlarmClockOption(opt)
        localRepository.saveAlarmClockOption(opt)
    }
    fun getAlarmClockOption(): Int =
        inMemoryRepository.getAlarmClockOption() ?: localRepository.getAlarmClockOption().also {
            inMemoryRepository.saveAlarmClockOption(it)
        }
}