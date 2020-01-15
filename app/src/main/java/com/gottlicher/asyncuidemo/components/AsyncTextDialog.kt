package com.gottlicher.asyncuidemo.components

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

data class TextDialogResult(
    val dialogResult: DialogResult,
    val text: String?
)

class AsyncTextDialog(
    private val title: Int,
    private val hintText: Int,
    private val positiveButtonText: Int? = null,
    private val negativeButtonText: Int? = null,
    private val neutralButtonText: Int? = null
) {

    suspend fun show(context: Context): TextDialogResult = suspendCancellableCoroutine { cont ->
        var cancelledByUser = false

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        val editText = makeEditText(context, hintText)
        builder.setView(editText)

        if (positiveButtonText != null) {
            builder.setPositiveButton(positiveButtonText) { _, _ ->
                cont.resume(TextDialogResult(DialogResult.POSITIVE, editText.text.toString()))
            }
        }

        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText) { _, _ ->
                cont.resume(TextDialogResult(DialogResult.NEGATIVE, editText.text.toString()))
            }
        }

        if (neutralButtonText != null) {
            builder.setNegativeButton(neutralButtonText) { _, _ ->
                cont.resume(TextDialogResult(DialogResult.NEUTRAL, editText.text.toString()))
            }
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

    private fun makeEditText(context: Context, hint: Int): EditText {
        val editText = EditText(context)
        editText.hint = context.resources.getString(hint)
        return editText
    }
}