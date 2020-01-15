package com.gottlicher.asyncuidemo.demos


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gottlicher.asyncuidemo.R
import com.gottlicher.asyncuidemo.components.AsyncTextDialog
import com.gottlicher.asyncuidemo.components.TextDialogResult
import kotlinx.android.synthetic.main.fragment_dialog_demo.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class TextInputDialogDemoFragment : Fragment(R.layout.fragment_text_input_dialog_demo) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDialogButton.setOnClickListener { onButtonClick() }
    }

    private fun onButtonClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val (result, text) = showDialogAndReturnResult()
                dialogResultText.text = "Dialog result is: $result, and text is: $text"
            } catch (e: CancellationException) {
                dialogResultText.text = "Dialog was dismissed"
            }
        }
    }

    private suspend fun showDialogAndReturnResult(): TextDialogResult {
        return AsyncTextDialog(
            title = R.string.dialog_title,
            hintText = R.string.dialog_text_hint,
            positiveButtonText = R.string.positive,
            negativeButtonText = R.string.negative,
            neutralButtonText = R.string.neutral
        ).show(context!!)
    }
}
