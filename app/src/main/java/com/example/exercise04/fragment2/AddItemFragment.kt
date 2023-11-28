package com.example.exercise04.fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.exercise04.DataItem
import com.example.exercise04.R
import com.example.exercise04.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment() {
    private lateinit var binding: FragmentAddItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val itemValue = binding.ratingBarItemValue.rating
            val itemChecked = binding.checkBoxItemType.isChecked

            var itemType = 0
            when (binding.editTextItemType.text.toString()) {
                "Coffee Mug" -> itemType = 0
                "Cup of Tea" -> itemType = 1
                "Energy drink" -> itemType = 2
                else -> itemType = 0
            }
            val dataItem = DataItem(itemValue, itemChecked, itemType)

            displayItemInfo(dataItem)
        }

        binding.cancelButton.setOnClickListener {
            navigateToList2Fragment()
        }
    }

    private fun displayItemInfo(dataItem: DataItem) {
        val navController = NavHostFragment.findNavController(this)
        val destinationId = R.id.nav_list2_fragment

        val args = Bundle().apply {
            putSerializable("data_item_key_2", dataItem)
        }
        navController.navigate(destinationId, args)
    }

    private fun navigateToList2Fragment() {
        val navController = NavHostFragment.findNavController(this)
        val destinationId = R.id.nav_list2_fragment
        navController.navigate(destinationId)
    }
}

