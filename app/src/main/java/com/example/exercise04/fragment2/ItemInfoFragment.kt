package com.example.exercise04.fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exercise04.DataBase.DBItem
import com.example.exercise04.R
import com.example.exercise04.databinding.FragmentItemInfoBinding

class ItemInfoFragment : Fragment() {

    private lateinit var binding: FragmentItemInfoBinding
    private var dataItem: DBItem? = null

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
            dataItem = args.getSerializable(DATA_ITEM_KEY) as? DBItem
        }


        if (dataItem != null) {
            displayItemInfo(dataItem)
        } else {
            binding.textViewItemName.text = "Item information not available"
        }

        binding.fbEdit.setOnClickListener {
            navigateToEditItemFragment()
        }
    }

    private fun displayItemInfo(dataItem: DBItem?) {
        if (dataItem != null) {
            with(binding) {
                textViewItemName.text = dataItem.item_name
                textViewItemType.text = dataItem.item_type
                textViewItemDate.text = "Creation date: " + dataItem.item_date
                imageViewItem.setImageResource(dataItem.item_image)
                ratingBarItemValue.rating = dataItem.item_rating
                checkBoxItemType.isChecked = dataItem.item_checked
                textViewItemName.text = dataItem.item_type
            }
        } else {
            binding.textViewItemName.text = "Item information not available"
        }
    }

    private fun navigateToEditItemFragment() {
        val navController = findNavController()
        val destinationId = R.id.action_nav_item_info_fragment_to_nav_edit_item_fragment
        val args = Bundle().apply {
            putSerializable(DATA_ITEM_KEY, dataItem)
        }
        navController.navigate(destinationId, args)
    }

    companion object {
        private const val DATA_ITEM_KEY = "data_item_key"

        @JvmStatic
        fun newInstance(dataItem: DBItem): ItemInfoFragment {
            val fragment = ItemInfoFragment()
            val args = Bundle().apply {
                putSerializable(DATA_ITEM_KEY, dataItem)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
