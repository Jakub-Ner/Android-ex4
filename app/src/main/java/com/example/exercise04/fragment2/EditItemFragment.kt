package com.example.exercise04.fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.exercise04.DataBase.DBItem
import com.example.exercise04.R
import com.example.exercise04.databinding.FragmentEditItemBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [EditItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentEditItemBinding
    private var item: DBItem? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null){
            item = args.getSerializable("data_item_key") as DBItem
        }
        binding.editTextItemName.setText(item?.item_name)
        binding.ratingBarItemValue.rating = item?.item_rating ?: 0.0f
        binding.checkBoxItemType.isChecked = item?.item_checked ?: false
        when (item?.item_type) {
            "Coffee Mug" -> {
                binding.rbCoffee.isChecked = true
            }
            "Tea Mug" -> {
                binding.rbTea.isChecked = true
            }
            "Energy Drink" -> {
                binding.rbEnergyDrink.isChecked = true
            }

        }

        binding.saveButton.setOnClickListener {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            item!!.item_name = binding.editTextItemName.text.toString()
            item!!.item_rating = binding.ratingBarItemValue.rating
            item!!.item_checked = binding.checkBoxItemType.isChecked
            item!!.item_date = LocalDateTime.now().format(formatter).toString()
            item!!.item_type = getType(binding.rg.checkedRadioButtonId)
            item!!.item_image = when(item!!.item_type){
                "Coffee Mug" -> R.drawable.coffee_mug
                "Cup of Tea" -> R.drawable.cup_of_tea
                "Energy drink" -> R.drawable.energy_drink
                else ->  R.drawable.coffee_mug
            }
            val repository = DataRepo2.getInstance(requireContext())
            repository.editItem(item!!)
            navigateToList2Fragment()
        }


        binding.cancelButton.setOnClickListener {
            navigateToList2Fragment()
        }
    }

    private fun navigateToList2Fragment() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate( R.id.nav_list2_fragment)
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