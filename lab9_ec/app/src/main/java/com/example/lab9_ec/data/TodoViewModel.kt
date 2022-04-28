package com.example.lab9_ec.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(private val dataStoreRepo: TodoRepo):ViewModel() {
    val toDo = dataStoreRepo.toDo.asLiveData()
    fun editTodo(title: String, description: String){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreRepo.editTodo(title, description)
        }
    }
}

class TodoViewModelFactory(private val dataStoreRepo: TodoRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(dataStoreRepo) as T
    }
}