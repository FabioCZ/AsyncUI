package com.gottlicher.asyncui2.components

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@SuppressLint("MissingPermission")
suspend fun Context.getCurrentLocation(): Location? = suspendCancellableCoroutine { cont ->
    val client =  FusedLocationProviderClient(this)
    val token = CancellationTokenSource()
    client.getCurrentLocation(PRIORITY_BALANCED_POWER_ACCURACY, token.token).apply {
        addOnFailureListener { Log.w("LOCATION", "Error: $it") }
        addOnSuccessListener { cont.resume(it) }
        addOnCanceledListener { cont.cancel() }
    }
    cont.invokeOnCancellation { token.cancel() }
}
