package com.example.exercise04

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.exercise04.databinding.FragmentList1Binding

class List1Fragment : Fragment() {


    lateinit var list1: ListView
    val data = DataRepo.getInstance()
    private lateinit var _binding: FragmentList1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentList1Binding.inflate(inflater, container, false)
        val rootView = _binding.root

        list1 = _binding.listview1
        val adapter1 = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_multiple_choice,
            data.item_text_list
        )

        list1.adapter = adapter1
        list1.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        list1.setOnItemClickListener { _, _, position, _ ->
            val checked = list1.isItemChecked(position)
            val itemText = data.item_text_list[position]

            val message = if (checked) {
                "Clicked $position: Checked $itemText"
            } else {
                "Clicked $position: Unchecked $itemText"
            }

            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        return rootView
    }

}