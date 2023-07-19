package com.gottlicher.asyncui2.demos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gottlicher.asyncui2.R
import com.gottlicher.asyncui2.components.getCurrentLocation
import com.gottlicher.asyncui2.databinding.FragmentLocationBinding
import com.gottlicher.asyncui2.util.viewBinding
import kotlinx.coroutines.launch

class LocationFragment: Fragment(R.layout.fragment_location) {
    private val binding by viewBinding(FragmentLocationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.getLocationButton.setOnClickListener {
            printLocation()
        }
    }

    private fun printLocation() = viewLifecycleOwner.lifecycleScope.launch {
        val location = requireContext().getCurrentLocation()
        binding.locationText.text = location.toString()
    }
}
