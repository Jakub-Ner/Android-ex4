package com.example.exercise04.photos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.exercise04.databinding.FragmentSwipePhotoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SwipePhotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SwipePhotoFragment : Fragment() {
    private lateinit var binding: FragmentSwipePhotoBinding
    private lateinit var dataRepo: DataRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSwipePhotoBinding.inflate(inflater, container, false)
        dataRepo = DataRepo.getinstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        var currentItemId = arguments?.getInt("selectedItemId") ?: 0
        Log.d("SwipePhotoFragment", "currentItemId: $currentItemId")

        val images = dataRepo.getList()
        val viewPager: ViewPager2 = binding.viewPager2
        viewPager.adapter = ViewPagerAdapterImage(this, images)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SwipePhotoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SwipePhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

class ViewPagerAdapterImage(
    swipePhotoFragment: SwipePhotoFragment,
    private val images: MutableList<DataItem>?
) : FragmentStateAdapter(swipePhotoFragment) {

    override fun getItemCount(): Int {
        return images?.size ?: 0
    }

    override fun createFragment(itemId: Int): Fragment {
        val fragment = ImageFragment()
        val bundle = Bundle()
        bundle.putString("imageUri", images!![itemId].uripath)
        fragment.arguments = bundle
        return fragment
    }


}

//class ViePagerAdapterImage