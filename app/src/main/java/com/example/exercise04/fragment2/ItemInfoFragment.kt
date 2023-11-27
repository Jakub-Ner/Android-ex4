package com.example.exercise04.fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.exercise04.DataItem
import com.example.exercise04.databinding.FragmentItemInfoBinding

class ItemInfoFragment : Fragment() {

    private lateinit var binding: FragmentItemInfoBinding
    private var dataItem: DataItem? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null) {
            dataItem = args.getSerializable(DATA_ITEM_KEY) as? DataItem
        }


        // Check if the item information is not null
        if (dataItem != null) {
            // Display item information in the UI
            displayItemInfo(dataItem)
        } else {
            // Handle the case when item information is not available
            binding.textViewItemName.text = "Item information not available"
        }
    }

    private fun displayItemInfo(dataItem: DataItem?) {
        if (dataItem != null) {
            with(binding) {
                textViewItemName.text = "Item Name: ${dataItem.text_main}"
                textViewItemValue.text = "Item Value: ${dataItem.text_2} ${dataItem.item_value}"

                // Example of using a RatingBar
                ratingBarItemValue.rating = dataItem.item_value.toFloat()

                // Example of using CheckBox
                checkBoxItemType.isChecked = dataItem.item_checked

                // Example of using another TextView
                textViewItemChecked.text =
                    "Item Type: ${if (dataItem.item_type) "Bus" else "Plane"}"
            }
        } else {
            binding.textViewItemName.text = "Item information not available"
        }
    }

    companion object {
        private const val DATA_ITEM_KEY = "data_item_key"

        @JvmStatic
        fun newInstance(dataItem: DataItem): ItemInfoFragment {
            val fragment = ItemInfoFragment()
            val args = Bundle().apply {
                putSerializable(DATA_ITEM_KEY, dataItem)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
