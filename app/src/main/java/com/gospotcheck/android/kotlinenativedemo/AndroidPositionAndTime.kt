package com.gospotcheck.android.kotlinenativedemo

import com.gospotcheck.android.mpp.PositionAndTime


data class AndroidPositionAndTime(
    override val lat: Double,
    override val lng: Double,
    override val timestamp: Long
) : PositionAndTime