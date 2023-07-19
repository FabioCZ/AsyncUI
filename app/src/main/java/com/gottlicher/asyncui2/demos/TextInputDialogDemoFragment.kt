package com.gottlicher.asyncui2.demos


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gottlicher.asyncui2.R
import com.gottlicher.asyncui2.components.AsyncTextDialog
import com.gottlicher.asyncui2.components.TextDialogResult
import com.gottlicher.asyncui2.databinding.FragmentTextInputDialogDemoBinding
import com.gottlicher.asyncui2.util.viewBinding
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class TextInputDialogDemoFragment : Fragment(R.layout.fragment_text_input_dialog_demo) {

    private val binding by viewBinding(FragmentTextInputDialogDemoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.showDialogButton.setOnClickListener { onButtonClick() }
    }

    private fun onButtonClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val (result, text) = showDialogAndReturnResult()
                binding.dialogResultText.text = "Dialog result is: $result, and text is: $text"
            } catch (e: CancellationException) {
                binding.dialogResultText.text = "Dialog was dismissed"
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
        ).show(requireContext())
    }
}
