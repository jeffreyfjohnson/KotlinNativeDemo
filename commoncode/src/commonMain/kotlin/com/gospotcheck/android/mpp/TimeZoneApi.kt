package com.gospotcheck.android.mpp

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.takeFrom
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON

internal expect val ApplicationDispatcher: CoroutineDispatcher

class TimeZoneApi {

    private val apiClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json = JSON.nonstrict).apply {
                setMapper(TimeZoneResponse::class, TimeZoneResponse.serializer())
            }
        }
    }

    suspend fun getTimeZone(positionAndTime: PositionAndTime): TimeZoneResponse = apiClient.get {
        url {
            takeFrom("http://api.timezonedb.com")
            path("v2.1", "get-time-zone")
            parameter("time", positionAndTime.timestamp.toString())
            parameter("key", "CMPGTEE6S4YB")
            parameter("by", "position")
            parameter("lat", positionAndTime.lat.toString())
            parameter("lng", positionAndTime.lng.toString())
            parameter("format", "json")
        }
    }

    fun getTimeZone(positionAndTime: PositionAndTime, timeZoneFoundAction: (TimeZoneResponse) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            val response = getTimeZone(positionAndTime)
            timeZoneFoundAction(response)
        }
    }

}

interface PositionAndTime {
    val lat: Double
    val lng: Double
    val timestamp: Long
}

@Serializable
data class TimeZoneResponse(
    val status: String,
    val message: String,
    val countryCode: String,
    val countryName: String,
    val zoneName: String,
    val abbreviation: String,
    val gmtOffset: Long,
    val dst: String,
    val zoneStart: Long,
    val zoneEnd: Long,
    val nextAbbreviation: String,
    val timestamp: Long,
    val formatted: String
)