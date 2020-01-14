package androidx.viewpager.widget //so that we can override setCurrentItemInternal

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager


class SlowViewPager : ViewPager {
    companion object {
        private const val VELOCITY = 10
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun setCurrentItemInternal(item: Int, smoothScroll: Boolean, always: Boolean) {
        setCurrentItemInternal(item, smoothScroll, always, VELOCITY)
    }


}