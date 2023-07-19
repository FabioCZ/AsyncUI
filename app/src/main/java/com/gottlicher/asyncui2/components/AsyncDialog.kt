package com.gottlicher.asyncui2.components

import android.app.AlertDialog
import android.content.Context
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

enum class DialogResult {
    POSITIVE,
    NEGATIVE,
    NEUTRAL
}

class AsyncAlertDialog(
    private val title: Int? = null,
    private val message: Int? = null,
    private val positiveButtonText: Int? = null,
    private val negativeButtonText: Int? = null,
    private val neutralButtonText: Int? = null
) {

    suspend fun show(context: Context): DialogResult = suspendCancellableCoroutine { cont ->

        var cancelledByUser = false

        val builder = AlertDialog.Builder(context)
        if (title != null) {
            builder.setTitle(title)
        }
        if (message != null) {
            builder.setMessage(message)
        }
        if (positiveButtonText != null) {
            builder.setPositiveButton(positiveButtonText) { _, _ -> cont.resume(DialogResult.POSITIVE) }
        }
        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText) { _, _ -> cont.resume(DialogResult.NEGATIVE) }
        }
        if (neutralButtonText != null) {
            builder.setNeutralButton(neutralButtonText) { _, _ -> cont.resume(DialogResult.NEUTRAL) }
        }

        val dialog = builder.create()

        dialog.setOnCancelListener {
            cancelledByUser = true
            cont.cancel()
        }

        cont.invokeOnCancellation {
            if (!cancelledByUser) dialog.cancel()
        }

        dialog.show()
    }
}
