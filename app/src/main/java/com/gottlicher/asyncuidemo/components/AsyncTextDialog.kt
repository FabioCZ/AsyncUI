package com.gottlicher.asyncuidemo.components

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import kotlinx.coroutines.CompletableDeferred

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

    suspend fun show(context: Context): TextDialogResult {

        val completion = CompletableDeferred<TextDialogResult>()

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        val editText = makeEditText(context, hintText)
        builder.setView(editText)

        if (positiveButtonText != null) {
            builder.setPositiveButton(positiveButtonText) { _, _ ->
                completion.complete(
                    TextDialogResult(DialogResult.POSITIVE, editText.text.toString())
                )
            }
        }

        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText) { _, _ ->
                completion.complete(
                    TextDialogResult(DialogResult.NEGATIVE, editText.text.toString())
                )
            }
        }

        if (neutralButtonText != null) {
            builder.setNegativeButton(neutralButtonText) { _, _ ->
                completion.complete(
                    TextDialogResult(DialogResult.NEUTRAL, editText.text.toString())
                )
            }
        }

        builder.create().show()
        return completion.await()
    }

    private fun makeEditText(context: Context, hint: Int): EditText {
        val editText = EditText(context)
        editText.hint = context.resources.getString(hint)
        return editText
    }
}