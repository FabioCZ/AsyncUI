package com.gottlicher.asyncuidemo.demos


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gottlicher.asyncuidemo.R
import kotlinx.android.synthetic.main.fragment_loading_demo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.util.Date
import java.util.concurrent.Executors


class LoadingDemoFragment : Fragment(R.layout.fragment_loading_demo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadOldSchoolButton.setOnClickListener { loadOldSchool() }
        loadCoroutineButton.setOnClickListener { loadCoroutines() }
    }

    private fun loadOldSchool() {
        val executor = Executors.newSingleThreadExecutor()
        val mainThreadHandler = Handler(Looper.getMainLooper())

        setLoadingState(true)
        executor.execute {
            val result = getCurrentTime()
            mainThreadHandler.post {
                setLoadingState(false)
                showResult(result)
            }
        }
    }

    private fun loadCoroutines() {
        viewLifecycleOwner.lifecycleScope.launch {
            setLoadingState(true)

            //val result = getCurrentTime()

            //withContext(Dispatchers.Default) will force the lambda to run on the ThreadPool, making sure the UI isn't blocked
            val result =  withContext(Dispatchers.Default) { getCurrentTime() }

            setLoadingState(false)
            showResult(result)
        }
    }

    @UiThread
    private fun showResult(result: String) {
        resultText.text = "Current time is $result"
    }

    @UiThread
    private fun setLoadingState(loading: Boolean) {
        resultText.visibility = if (loading) View.GONE else View.VISIBLE
        progressBar.visibility = if(loading) View.VISIBLE else View.GONE
    }

    private fun getCurrentTime(): String {
        Thread.sleep(1000) // do a blocking operation
        return DateFormat.getDateTimeInstance().format(Date())
    }
}
