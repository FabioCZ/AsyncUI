package com.gottlicher.asyncui2.components

import androidx.viewpager.widget.ViewPager
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun ViewPager.awaitPageChange() = suspendCancellableCoroutine<Int> { cont ->
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                cont.resume(this@awaitPageChange.currentItem)
                this@awaitPageChange.removeOnPageChangeListener(this)
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            // no-op
        }

        override fun onPageSelected(position: Int) {
            // no-op
        }
    })
}
