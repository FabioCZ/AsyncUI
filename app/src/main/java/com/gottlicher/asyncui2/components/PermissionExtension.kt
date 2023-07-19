package com.gottlicher.asyncui2.components

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun ComponentActivity.requestPermission(permission: String): Boolean = suspendCancellableCoroutine { cont ->
    val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        cont.resume(granted)
    }
    cont.invokeOnCancellation { launcher.unregister() }
    launcher.launch(permission)
}
