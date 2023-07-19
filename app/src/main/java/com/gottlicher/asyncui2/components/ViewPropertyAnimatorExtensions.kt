package com.gottlicher.asyncui2.components

import android.animation.Animator
import android.view.ViewPropertyAnimator
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun ViewPropertyAnimator.startAsync() = suspendCancellableCoroutine<Unit> { cont ->
    cont.invokeOnCancellation { cancel() }
    setListener(object: Animator.AnimatorListener {

        override fun onAnimationRepeat(p0: Animator) {}

        override fun onAnimationEnd(p0: Animator) {
            cont.resume(Unit)
        }

        override fun onAnimationCancel(p0: Animator) {
            cont.cancel()
        }

        override fun onAnimationStart(p0: Animator) {}
    })
    start()
}
