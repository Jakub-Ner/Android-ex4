package com.example.exercise04.fragment2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise04.DataItem
import com.example.exercise04.R
import com.example.exercise04.databinding.FragmentList2Binding
import com.example.exercise04.databinding.ListRowBinding

class List2Fragment : Fragment() {

    private lateinit var _binding: FragmentList2Binding

    val dataRepo = DataRepo2.getInstance()
    val adapter = MyAdapter(dataRepo.getData())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentList2Binding.inflate(inflater, container, false)
        val recView = _binding.recView
        recView.layoutManager = LinearLayoutManager(requireContext())

        val adapter2 = DataRepo2.getInstance().getData()?.let { MyAdapter(it) }
        recView.adapter = adapter2
        setHasOptionsMenu(true)

        return _binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val navController = findNavController()
                val destinationId = R.id.nav_add_item_fragment
                navController.navigate(destinationId)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    inner class MyAdapter(var data: MutableList<DataItem>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        inner class MyViewHolder(viewBinding: ListRowBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
            val tv1: TextView = viewBinding.lrowName
            val tv2: TextView = viewBinding.lrowPower
            val img: ImageView = viewBinding.lrowImage
            val cBox: CheckBox = viewBinding.lrowCheckBox

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = ListRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.tv1.text = when(data[position].itemType){
                0 -> "Coffee Mug"
                1 -> "Cup of Tea"
                2 -> "Energy Drink"
                else -> "Unknown"
            }
            holder.tv2.text = data[position].text2 + data[position].itemValue
            holder.cBox.isChecked = data[position].item_checked
            holder.itemView.setOnClickListener {
                showItemInfoFragment(data[position])
            }
            holder.itemView.setOnLongClickListener {
                if (dataRepo.deleteItem(position))
                    notifyDataSetChanged()
                true
            }

            holder.cBox.setOnClickListener { v ->
                data[position].item_checked = (v as CheckBox).isChecked
                Toast.makeText(
                    requireContext(),
                    "Selected/Unselected: " + (position + 1),
                    Toast.LENGTH_SHORT
                ).show()
            }
            when (data[position].itemType) {
                0 -> holder.img.setImageResource(R.drawable.pngwing_com)
                1 -> holder.img.setImageResource(R.drawable.pngwing_com__1_)
                2 -> holder.img.setImageResource(R.drawable.energy_drink)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ...

        val args = arguments
        if (args != null) {
            val dataItem = args.getSerializable("data_item_key_2") as? DataItem
            if (dataItem != null) {
                dataRepo.addItem(dataItem)

                adapter.notifyItemInserted(dataRepo.getData().size - 1)
            }
        }
    }

    private fun showItemInfoFragment(dataItem: DataItem) {
        val navController = findNavController()
        val destinationId = R.id.nav_item_info_fragment
        val dataItem2 = dataItem
        val args = Bundle().apply {
            putSerializable("data_item_key", dataItem2)
        }
        navController.navigate(destinationId, args)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            List2Fragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
