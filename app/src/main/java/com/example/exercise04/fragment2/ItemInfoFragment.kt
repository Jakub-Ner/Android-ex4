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


        if (dataItem != null) {
            displayItemInfo(dataItem)
        } else {
            binding.textViewItemName.text = "Item information not available"
        }
    }

    private fun displayItemInfo(dataItem: DataItem?) {
        if (dataItem != null) {
            with(binding) {
                textViewItemValue.text = "${dataItem.text2} ${dataItem.itemValue}"

                ratingBarItemValue.rating = dataItem.itemValue.toFloat()
                checkBoxItemType.isChecked = dataItem.item_checked
                when(dataItem.itemType){
                    0 -> textViewItemName.text = "Item Type: Coffee Mug"
                    1 -> textViewItemName.text = "Item Type: Cup of Tea"
                    2 -> textViewItemName.text = "Item Type: Energy drink"
                }
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
