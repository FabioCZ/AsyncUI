package com.gottlicher.asyncuidemo.demos

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gottlicher.asyncuidemo.R
import com.gottlicher.asyncuidemo.components.startAsync
import kotlinx.android.synthetic.main.fragment_async_animator.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AsyncAnimatorFragment : Fragment(R.layout.fragment_async_animator) {

    private var animationJob: Deferred<Unit>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cancelButton.setOnClickListener { cancelAnimation() }
        startButton.setOnClickListener { animateAndUpdateProgress() }
    }

    private fun animateAndUpdateProgress() = viewLifecycleOwner.lifecycleScope.launch {
        progressIndicator.text = "Animation started"
        animationJob = async { animateText() }
        animationJob?.await()
        progressIndicator.text = "Animation done"
    }

    private fun cancelAnimation() {
        progressIndicator.text = "Animation cancelled"
        animationJob.takeIf { it?.isActive == true }?.cancel()
    }

    private suspend fun animateText() {
        movingText.translationX = -600f
        return movingText.animate()
            .setDuration(3000)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .translationX(0f)
            .startAsync()
    }
}
