package com.example.exercise04.photos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.example.exercise04.databinding.FragmentImageBinding


class ViewPagerImageAdapter(
    swipePhotoFragment: SwipePhotoFragment,
    private val images: MutableList<DataItem>?
) : FragmentStateAdapter(swipePhotoFragment) {

    override fun getItemCount(): Int {
        return images?.size ?: 0
    }

    override fun createFragment(itemId: Int): Fragment {
        val fragment = ImageFragment()
        val bundle = Bundle()
        Log.d("SwipePhotoFragment", "itemId: $itemId")
        bundle.putString("imageUri", images!![itemId].uripath)
        fragment.arguments = bundle
        return fragment
    }
}


class ImageFragment : Fragment() {
    private lateinit var binding: FragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        val imageResId = requireArguments().getString("imageUri")
        Log.d("SwipePhotoFragment", "imageResId: $imageResId")
        Glide.with(binding.imageView.context).load(imageResId).into(binding.imageView)
        return binding.root
    }

}