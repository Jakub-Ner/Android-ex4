package com.example.exercise04.ui.Fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.exercise04.databinding.FragmentAddItemInfoBinding
import com.example.exercise04.DataItem
import com.example.exercise04.R

class AddItemInfoFragment : Fragment() {

    private lateinit var binding: FragmentAddItemInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up onClickListener for a button or any other UI interaction to handle user input
        binding.saveButton.setOnClickListener {
            // Retrieve user input from EditText fields
            val itemName = binding.editTextItemName.text.toString()
            val itemValue = binding.editTextItemValue.text.toString().toIntOrNull() ?: 0.0
            val itemChecked = binding.checkBoxItemType.isChecked
            val itemType = binding.editTextItemType.text.toString()

            val dataItem = DataItem(itemName, itemValue,  itemChecked,  itemType)

            displayItemInfo(dataItem)
        }

        binding.cancelButton.setOnClickListener {
            // Navigate back to List2Fragment without saving the data
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

