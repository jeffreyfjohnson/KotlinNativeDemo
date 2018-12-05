package com.gospotcheck.android.mpp


class Counter(private val storage: Storage) {
    companion object {
        private const val COUNT_KEY = "count"
        private const val STEP = 2
    }

    fun getCurrent(): Long = storage.retrieveNum(COUNT_KEY)

    fun increment(): Long {
        val currentNum = getCurrent()
        val newNum = currentNum + STEP
        storage.saveNum(COUNT_KEY, newNum)
        return newNum
    }

    fun decrement(): Long {
        val currentNum = getCurrent()
        val newNum = currentNum - STEP
        storage.saveNum(COUNT_KEY, newNum)
        return newNum
    }
}

interface Storage {
    fun saveNum(key: String, num: Long)
    fun retrieveNum(key: String): Long
}