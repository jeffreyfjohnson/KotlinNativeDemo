package com.gospotcheck.android.kotlinenativedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gospotcheck.android.mpp.Counter
import com.gospotcheck.android.mpp.TimeZoneApi
import com.gospotcheck.android.mpp.createApplicationScreenMessage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainTextView.text = createApplicationScreenMessage()

        val counter = Counter(PlatformStorage(this))

        val api = TimeZoneApi()

        updateCountText(counter.getCurrent())

        minusButton.setOnClickListener {
            updateCountText(counter.decrement())
        }
        plusButton.setOnClickListener {
            updateCountText(counter.increment())
        }

        latEditText.setText("39.7392")
        lngEditText.setText("-104.9903")

        searchButton.setOnClickListener {
            searchTimeZone(
                api,
                latEditText.text.toString().toDouble(),
                lngEditText.text.toString().toDouble(),
                System.currentTimeMillis() / 1000L
            )
        }
    }

    private fun updateCountText(num: Long) {
        countTextView.text = num.toString()
    }

    private fun searchTimeZone(api: TimeZoneApi, lat: Double, lng: Double, timestamp: Long) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = api.getTimeZone(lat, lng, timestamp)
            timeZoneTextView.text = response.zoneName
        }
    }
}