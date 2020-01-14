package com.gottlicher.asyncuidemo.demos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gottlicher.asyncuidemo.R
import com.gottlicher.asyncuidemo.components.AsyncAlertDialog
import com.gottlicher.asyncuidemo.components.DialogResult
import kotlinx.android.synthetic.main.fragment_dialog_demo.*
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException


class DialogDemoFragment : Fragment(R.layout.fragment_dialog_demo) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDialogButton.setOnClickListener { onShowClick() }
        showDialogAndCancel.setOnClickListener { onShowAndCancelClick() }
    }

    private fun onShowClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = showDialogAndReturnResult()
            dialogResultText.text = "Dialog result is: $result"
        }
    }

    private fun onShowAndCancelClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            val job = async { showDialogAndReturnResult() }
            delay(1000)
            job.cancel(CancellationException("Cancel me"))
            try {
                val result = job.await()
                dialogResultText.text = "Dialog result is: $result"
            } catch (e: CancellationException) {
                dialogResultText.text = "Dialog was dismissed"
            }
        }
    }

    private suspend fun showDialogAndReturnResult(): DialogResult {
        return AsyncAlertDialog(
            title = R.string.dialog_title,
            message = R.string.dialog_subtitle,
            positiveButtonText = R.string.positive,
            negativeButtonText = R.string.negative,
            neutralButtonText = R.string.neutral
        ).show(context!!)
    }
}
