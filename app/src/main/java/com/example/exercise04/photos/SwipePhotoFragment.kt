package com.example.exercise04.photos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.exercise04.R
import com.example.exercise04.databinding.FragmentSwipePhotoBinding


class SwipePhotoFragment : Fragment() {
    private lateinit var binding: FragmentSwipePhotoBinding
    private lateinit var dataRepo: DataRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSwipePhotoBinding.inflate(inflater, container, false)
        dataRepo = DataRepo.getinstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentItemId = arguments?.getInt("selectedItemId") ?: 0
        Log.d("SwipePhotoFragment", "currentItemId: $currentItemId")

        val images = dataRepo.getList()
        val viewPager: ViewPager2 = binding.viewPager2
        viewPager.adapter = ViewPagerImageAdapter(this, images)
        viewPager.setCurrentItem(currentItemId, false)

        val btnConfirmImage = binding.fab
        btnConfirmImage.setOnClickListener {
            val currentPosition = viewPager.currentItem
            if (currentPosition < images!!.size) {
                val selectedImageUri = images[currentPosition].uripath
                val data = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                data.edit().putString("image", selectedImageUri).apply()
                findNavController().navigate(R.id.action_swipePhotoFragment_to_nav_home)
            }
        }
    }


}

