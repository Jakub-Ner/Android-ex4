package com.example.exercise04.ui.Fragment2

import com.example.exercise04.DataItem

class DataRepo2 {
    private val LIST_SIZE = 3
    private lateinit var dataList: MutableList<DataItem>

    companion object{
        private var INSTANCE: DataRepo2? = null
        fun getInstance(): DataRepo2{
            if(INSTANCE == null){
                INSTANCE = DataRepo2()
            }

            return INSTANCE!!
        }
    }

    fun getData() : MutableList<DataItem> {
        return dataList
    }

    fun deleteItem(position: Int): Boolean {
        dataList.removeAt(position)
        return true
    }

    fun addItem(dataItem: DataItem) {
        dataList.add(dataItem)
    }

    init {
        dataList = MutableList(LIST_SIZE) { i -> DataItem(i)}
    }
}