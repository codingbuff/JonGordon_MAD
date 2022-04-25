package com.example.chordjam.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.chordjam.dataGraveyard.ProgressionsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgressionsViewModel(private val dataStoreRepo: ProgressionsRepo): ViewModel() {
    val progressions = dataStoreRepo.progressions.asLiveData()
    fun addProgression(name: String, chords: MutableList<String>){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.addProgression(name, chords)
        }
    }
    fun getProgressionAt(index: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.getProgressionAt(index)
        }
    }
}

class ProgressionsViewModelFactory(private val dataStoreRepo: ProgressionsRepo): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>):T{
        return ProgressionsViewModel(dataStoreRepo) as T
    }
}