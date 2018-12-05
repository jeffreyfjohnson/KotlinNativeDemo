package com.gospotcheck.android.mpp

import platform.Foundation.NSUserDefaults

class PlatformStorage(): Storage {
    private val delegate: NSUserDefaults = NSUserDefaults.standardUserDefaults()
    override fun saveNum(key: String, num: Long) = delegate.setInteger(num, key)
    override fun retrieveNum(key: String) = delegate.integerForKey(key)
}