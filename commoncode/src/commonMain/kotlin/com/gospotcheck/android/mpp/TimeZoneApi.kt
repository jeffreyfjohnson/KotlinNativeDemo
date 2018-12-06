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
import kotlinx.serialization.json.json

internal expect val ApplicationDispatcher: CoroutineDispatcher

class TimeZoneApi {

    private val apiClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer().apply {
                setMapper(TimeZoneResponse::class, TimeZoneResponse.serializer())
                JSON.nonstrict
            }
        }
    }

    suspend fun getTimeZone(lat: Double, lng: Double, timestamp: Long): TimeZoneResponse = apiClient.get {
        url {
            takeFrom("http://api.timezonedb.com")
            path("v2.1", "get-time-zone")
            parameter("time", timestamp.toString())
            parameter("key", "CMPGTEE6S4YB")
            parameter("by", "position")
            parameter("lat", lat.toString())
            parameter("lng", lng.toString())
            parameter("format", "json")
        }
    }

    fun getTimeZone(lat: Double, lng: Double, timestamp: Long, timeZoneFoundAction: (TimeZoneResponse) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            val response = getTimeZone(lat, lng, timestamp)
            timeZoneFoundAction(response)
        }
    }
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