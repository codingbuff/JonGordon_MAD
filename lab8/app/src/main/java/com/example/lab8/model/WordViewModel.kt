package com.example.lab8.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WordViewModel: ViewModel() {
    val defList = MutableLiveData<ArrayList<Word>>()
    fun updateList(newList: ArrayList<Word>){
        defList.value = newList
    }
}