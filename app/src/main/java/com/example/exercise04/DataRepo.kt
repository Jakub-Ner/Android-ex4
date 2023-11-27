package com.example.exercise04

class DataRepo {

    val LIST_SIZE = 15
    val item_text_list = Array(LIST_SIZE) { i -> "Item name $i" }

    companion object{
        private var INSTANCE: DataRepo? = null

        fun getInstance(): DataRepo {
            if (INSTANCE == null) {
                INSTANCE = DataRepo()
            }
            return INSTANCE as DataRepo
        }
    }
}