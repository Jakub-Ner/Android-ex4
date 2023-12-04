package com.example.exercise04.fragment2

import android.app.AlertDialog
import android.os.Bundle
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
import com.example.exercise04.DataBase.DBItem
import com.example.exercise04.R
import com.example.exercise04.databinding.FragmentList2Binding
import com.example.exercise04.databinding.ListRowBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class List2Fragment : Fragment() {

    private lateinit var _binding: FragmentList2Binding
    lateinit var dataRepo: DataRepo2
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataRepo = DataRepo2.getInstance(requireContext())
        adapter = MyAdapter(dataRepo.getData() ?: mutableListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentList2Binding.inflate(inflater, container, false)

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

    inner class MyAdapter(var data: MutableList<DBItem>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        inner class MyViewHolder(viewBinding: ListRowBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
            val tv1: TextView = viewBinding.lrowName
            val tv2: TextView = viewBinding.lrowPower
            val img: ImageView = viewBinding.lrowImage
            val cBox: CheckBox = viewBinding.lrowCheckBox

        }

        fun updateData(newData: MutableList<DBItem>) {
            data = newData
            notifyDataSetChanged()
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
            holder.tv1.text = data[position].item_name


            holder.tv2.text = data[position].item_type
            holder.cBox.isChecked = data[position].item_checked
            holder.itemView.setOnClickListener {
                showItemInfoFragment(data[position])
            }
            holder.itemView.setOnLongClickListener {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setTitle("Delete Item")
                alertDialog.setMessage("Are you sure you want to delete this item?")
                alertDialog.setPositiveButton("Yes") { _, _ ->
                    if (dataRepo.deleteItem(data[position]))
                        data = dataRepo.getData()!!
                        notifyDataSetChanged()
                }
                alertDialog.setNegativeButton("No") { _, _ -> }
                alertDialog.create().show()
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
            holder.img.setImageResource(data[position].item_image)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recView = _binding.recView
        recView.layoutManager = LinearLayoutManager(requireContext())
        recView.adapter = adapter

        val args = arguments
        if (args != null) {
            val dataItem = args.getSerializable("data_item_key_2") as? DBItem
            if (dataItem != null) {
                dataRepo.addItem(dataItem)

                adapter.updateData(dataRepo.getData() ?: mutableListOf())
                adapter.notifyDataSetChanged()
            }
        }
        val fabAddNew = view.findViewById<FloatingActionButton>(R.id.fbAddNew)

        fabAddNew.setOnClickListener {
            findNavController().navigate(R.id.nav_add_item_fragment)
        }
    }



    private fun showItemInfoFragment(dataItem: DBItem) {
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
