package com.gottlicher.asyncuidemo.demos

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.gottlicher.asyncuidemo.R

import kotlinx.android.synthetic.main.activity_with_result.*

class ActivityWithResult : AppCompatActivity(R.layout.activity_with_result) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        return1Button.setOnClickListener { finishWithResult(1) }
        return2Button.setOnClickListener { finishWithResult(2) }
        return3Button.setOnClickListener { finishWithResult(3) }
    }

    private fun finishWithResult(result: Int) {
        setResult(result)
        finish()
    }
}
