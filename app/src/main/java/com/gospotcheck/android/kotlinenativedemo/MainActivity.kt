package com.gospotcheck.android.kotlinenativedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gospotcheck.android.mpp.Counter
import com.gospotcheck.android.mpp.createApplicationScreenMessage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainTextView.text = createApplicationScreenMessage()

        val counter = Counter(PlatformStorage(this))

        updateCountText(counter.getCurrent())

        minusButton.setOnClickListener {
            updateCountText(counter.decrement())
        }
        plusButton.setOnClickListener {
            updateCountText(counter.increment())
        }
    }

    private fun updateCountText(num: Long) {
        countTextView.text = num.toString()
    }

}