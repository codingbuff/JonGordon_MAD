package com.example.chordjam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgViewModel(private val dataStoreRepo: ProgRepo):ViewModel() {
    val progression = dataStoreRepo.progressions.asLiveData()
    fun editProgression(chordList: MutableList<String>){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.editProgression(chordList)
        }
    }
}

class ProgViewModelFactory(private val dataStoreRepo: ProgRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProgViewModel(dataStoreRepo) as T
    }
}