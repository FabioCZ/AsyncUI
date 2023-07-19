package com.gottlicher.asyncui2.demos

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import com.gottlicher.asyncui2.R
import com.gottlicher.asyncui2.components.awaitPageChange
import com.gottlicher.asyncui2.databinding.FragmentViewPagerDemoBinding
import com.gottlicher.asyncui2.util.viewBinding
import kotlinx.coroutines.launch


class ViewPagerDemoFragment : Fragment(R.layout.fragment_view_pager_demo) {

    private val binding by viewBinding(FragmentViewPagerDemoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter()
        binding.nextPageButton.setOnClickListener { goToNextPage() }
    }

    private fun goToNextPage() = viewLifecycleOwner.lifecycleScope.launch {
        binding.transitionStateText.text = "Page is transitioning"
        val nextItem = (binding.viewPager.currentItem + 1) % binding.viewPager.adapter!!.count
        binding.viewPager.currentItem = nextItem
        binding.viewPager.awaitPageChange()
        binding.transitionStateText.text = "Page finished transitioning"
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
