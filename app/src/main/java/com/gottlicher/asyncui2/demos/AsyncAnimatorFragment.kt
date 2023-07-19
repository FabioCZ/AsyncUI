package com.gottlicher.asyncui2.demos

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gottlicher.asyncui2.R
import com.gottlicher.asyncui2.components.startAsync
import com.gottlicher.asyncui2.databinding.FragmentAsyncAnimatorBinding
import com.gottlicher.asyncui2.util.viewBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class AsyncAnimatorFragment : Fragment(R.layout.fragment_async_animator) {

    private val binding by viewBinding(FragmentAsyncAnimatorBinding::bind)

    private var animationJob: Deferred<Unit>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener { cancelAnimation() }
        binding.startButton.setOnClickListener { animateAndUpdateProgress() }
    }

    private fun animateAndUpdateProgress() = viewLifecycleOwner.lifecycleScope.launch {
        binding.progressIndicator.text = "Animation started"
        animationJob = async { animateText() }
        animationJob?.await()
        binding.progressIndicator.text = "Animation done"
    }

    private fun cancelAnimation() {
        binding.progressIndicator.text = "Animation cancelled"
        animationJob.takeIf { it?.isActive == true }?.cancel()
    }

    private suspend fun animateText() {
        binding.movingText.translationX = -600f
        return binding.movingText.animate()
            .setDuration(3000)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .translationX(0f)
            .startAsync()
    }
}
