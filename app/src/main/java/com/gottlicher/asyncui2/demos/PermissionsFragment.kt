package com.gottlicher.asyncui2.demos

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.PermissionRequest
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gottlicher.asyncui2.R
import com.gottlicher.asyncui2.components.requestPermission
import com.gottlicher.asyncui2.databinding.FragmentPermissionsBinding
import com.gottlicher.asyncui2.util.viewBinding
import com.markodevcic.peko.PermissionRequester
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PermissionsFragment: Fragment(R.layout.fragment_permissions) {
    private val binding by viewBinding(FragmentPermissionsBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.requestPermission.setOnClickListener {
            requestPermission()
        }
    }

    private fun requestPermission() = lifecycleScope.launch {
        val requester = PermissionRequester.instance()
        val request = requester.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        val result = request.first()
        binding.permissionResult.text = "Permission granted: $result"
    }
}
