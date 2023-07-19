package com.gottlicher.asyncui2.demos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gottlicher.asyncui2.R
import com.gottlicher.asyncui2.components.AsyncAlertDialog
import com.gottlicher.asyncui2.components.DialogResult
import com.gottlicher.asyncui2.databinding.FragmentDialogDemoBinding
import com.gottlicher.asyncui2.util.viewBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException


class DialogDemoFragment : Fragment(R.layout.fragment_dialog_demo) {

    private val binding by viewBinding(FragmentDialogDemoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.showDialogButton.setOnClickListener { onShowClick() }
        binding.showDialogAndCancel.setOnClickListener { onShowAndCancelClick() }
    }

    private fun onShowClick() = viewLifecycleOwner.lifecycleScope.launch {
        try {
            val result = showDialogAndReturnResult()
            binding.dialogResultText.text = "Dialog result is: $result"
        } catch (e: CancellationException) {
            binding.dialogResultText.text = "Dialog was dismissed"
        }
    }

    private fun onShowAndCancelClick() = viewLifecycleOwner.lifecycleScope.launch {
        val job = async { showDialogAndReturnResult() }
        delay(1000)
        job.cancel(CancellationException("Cancel me"))
        try {
            val result = job.await()
            binding.dialogResultText.text = "Dialog result is: $result"
        } catch (e: CancellationException) {
            binding.dialogResultText.text = "Dialog was dismissed"
        }
    }

    private suspend fun showDialogAndReturnResult(): DialogResult {
        return AsyncAlertDialog(
            title = R.string.dialog_title,
            message = R.string.dialog_subtitle,
            positiveButtonText = R.string.positive,
            negativeButtonText = R.string.negative,
            neutralButtonText = R.string.neutral
        ).show(requireContext())
    }
}
