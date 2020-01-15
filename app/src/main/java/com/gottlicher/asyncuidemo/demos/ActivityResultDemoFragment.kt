package com.gottlicher.asyncuidemo.demos


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gottlicher.asyncuidemo.R
import kotlinx.android.synthetic.main.fragment_activity_result_demo.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch

data class ActivityResult(
    val requestCode: Int,
    val resultCode: Int,
    val data: Intent?
)

// Do not use this - what would happen if the device is rotated and the target activity is recreated?
class ActivityResultDemoFragment : Fragment(R.layout.fragment_activity_result_demo) {

    companion object {
        private const val REQUEST_CODE = 5
    }

    private var deferred: CompletableDeferred<ActivityResult> = CompletableDeferred()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startActivityButton.setOnClickListener { onStartActivityClicked() }
    }


    private fun onStartActivityClicked() = viewLifecycleOwner.lifecycleScope.launch {
        val result = startActivityAndAwaitResult(Intent(context, ActivityWithResult::class.java), REQUEST_CODE)
        if (result.requestCode == REQUEST_CODE) {
            resultText.text = "Result is ${result.resultCode}"
        } else {
            resultText.text = "Request codes do not match"
        }
    }

    // Does not work if activity is destroyed
    private suspend fun startActivityAndAwaitResult(intent: Intent, requestCode: Int): ActivityResult {
        startActivityForResult(intent, requestCode)
        deferred.cancel()
        deferred = CompletableDeferred()
        return deferred.await()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        deferred.complete(ActivityResult(requestCode, resultCode, data))
    }
}
