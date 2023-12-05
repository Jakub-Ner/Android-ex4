package com.example.exercise04.fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.exercise04.DataBase.DBItem
import com.example.exercise04.fragment2.DataRepo2
import com.example.exercise04.R
import com.example.exercise04.databinding.FragmentAddItemBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
            val itemName = binding.editTextItemName.text.toString()
            val itemRating = binding.ratingBarItemValue.rating
            val itemChecked = binding.checkBoxItemType.isChecked
            val itemTypeId = binding.rg.checkedRadioButtonId
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val itemDate: String = LocalDateTime.now().format(formatter).toString()

            val item = DBItem(itemName, itemDate, itemRating, getType(itemTypeId), itemChecked)
            val repository = DataRepo2.getInstance(requireContext())
            if (repository.addItem(item)) {
                Toast.makeText(requireContext(), "Item added to the database", Toast.LENGTH_SHORT).show()
//                parentFragmentManager.setFragmentResult("item_added", Bundle.EMPTY)
            }

            val navController = NavHostFragment.findNavController(this)
            navController.navigate( R.id.nav_list2_fragment)
        }


        binding.cancelButton.setOnClickListener {
            navigateToList2Fragment()
        }
    }

    private fun getType(checkedId: Int): String {
        when (checkedId) {
            R.id.rbCoffee -> {
                return "Coffee Mug"
            }

            R.id.rbTea -> {
                return "Cup of Tea"
            }

            R.id.rbEnergyDrink -> {
                return "Energy drink"
            }
        }
        return ""
    }


//    private fun displayItemInfo(dataItem: DataItem) {
//        val navController = NavHostFragment.findNavController(this)
//        val destinationId = R.id.nav_list2_fragment
//
//        val args = Bundle().apply {
//            putSerializable("data_item_key_2", dataItem)
//        }
//        navController.navigate(destinationId, args)
//    }

    private fun navigateToList2Fragment() {
        val navController = NavHostFragment.findNavController(this)
        val destinationId = R.id.nav_list2_fragment
        navController.navigate(destinationId)
    }
}

