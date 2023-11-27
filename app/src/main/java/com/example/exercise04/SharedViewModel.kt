package com.example.exercise04

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _sharedText = MutableLiveData<String>()
    private val _sharedText2 = MutableLiveData<String>()
    val sharedText: LiveData<String> get() = _sharedText
    val sharedText2: LiveData<String> get() = _sharedText2
    fun updateText(newText: String) {
        _sharedText.value = newText
    }

    fun updateText2(newText: String) {
        _sharedText2.value = newText
    }
}
