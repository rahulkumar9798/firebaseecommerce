package com.example.firebaseecommerce.productDetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(val fm: FragmentManager, val lifecycle: Lifecycle): FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return DescriptionFragment()
            }
            1 -> {
                return SpecificationsFragment()
            }
            else -> {
                return ReviewsFragment()
            }
        }
    }
}