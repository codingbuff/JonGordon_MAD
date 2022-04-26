package com.example.lab9.data
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RandomThoughtViewModel(private val dataStoreRepo: DataStoreRepo):ViewModel() {
    val randomThought = dataStoreRepo.readFromDataStore.asLiveData()

    fun saveThought(thought: String, dateTime: String){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.saveToDataStore(thought,dateTime)
        }
    }
}

class RandomThoughtViewModelFactory(private val dataStoreRepo: DataStoreRepo): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>):T{
        return RandomThoughtViewModel(dataStoreRepo) as T
    }
}