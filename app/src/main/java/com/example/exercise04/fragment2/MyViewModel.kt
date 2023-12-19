package com.example.exercise04.fragment2

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.exercise04.DataBase.DBItem
import kotlinx.coroutines.launch

class MyViewModel(context: Context): ViewModel() {
    private lateinit var dataRepo: DataRepo2

    init {
        dataRepo = DataRepo2.getInstance(context)
    }

    fun getDataList2() = dataRepo.getData2()

    fun getDataList3() = dataRepo.getData3()

    fun addItem(item: DBItem) = viewModelScope.launch { dataRepo.addItem(item) }

    fun deleteItem(item: DBItem) = viewModelScope.launch { dataRepo.deleteItem(item) }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return super.create(modelClass)
            }
        }
    }
}