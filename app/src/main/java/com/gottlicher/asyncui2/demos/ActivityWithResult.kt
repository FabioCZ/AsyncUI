package com.gottlicher.asyncui2.demos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gottlicher.asyncui2.R
import com.gottlicher.asyncui2.databinding.ActivityWithResultBinding
import com.gottlicher.asyncui2.util.viewBinding


class ActivityWithResult : AppCompatActivity(R.layout.activity_with_result) {

    private val binding by viewBinding(ActivityWithResultBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.return1Button.setOnClickListener { finishWithResult(1) }
        binding.return2Button.setOnClickListener { finishWithResult(2) }
        binding.return3Button.setOnClickListener { finishWithResult(3) }
    }

    private fun finishWithResult(result: Int) {
        setResult(result)
        finish()
    }
}
