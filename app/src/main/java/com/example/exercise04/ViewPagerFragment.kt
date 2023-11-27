package com.example.exercise04

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)


        val vpAdapter = MyPagerAdapter2(requireActivity())


        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = vpAdapter


        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)


        TabLayoutMediator(tabLayout, viewPager, object : TabLayoutMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

                when (position) {
                    0 -> tab.text = "Tab 1"
                    1 -> tab.text = "Tab 2"
                }
            }
        }).attach()

        return view
    }
}