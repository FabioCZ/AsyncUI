package com.gottlicher.asyncuidemo.demos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import com.gottlicher.asyncuidemo.R
import com.gottlicher.asyncuidemo.components.awaitPageChange
import kotlinx.android.synthetic.main.fragment_view_pager_demo.*
import kotlinx.coroutines.launch


class ViewPagerDemoFragment : Fragment(R.layout.fragment_view_pager_demo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = ViewPagerAdapter()
        nextPageButton.setOnClickListener { goToNextPage() }
    }

    private fun goToNextPage() = viewLifecycleOwner.lifecycleScope.launch {
        transitionStateText.text = "Page is transitioning"
        val nextItem = (viewPager.currentItem + 1) % viewPager.adapter!!.count
        viewPager.currentItem = nextItem
        viewPager.awaitPageChange()
        transitionStateText.text = "Page finished transitioning"
    }

    class ViewPagerAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, obj: Any) = view == obj

        override fun getCount() = 5

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = TextView(container.context).apply {
                text = "Page no $position"
            }
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View)
        }
    }
}
