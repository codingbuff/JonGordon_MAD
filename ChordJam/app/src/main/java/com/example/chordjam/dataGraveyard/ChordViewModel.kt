//package com.example.chordjam.dataGraveyard
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.asLiveData
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class ChordViewModel(private val dataStoreRepo: ChordRepo):ViewModel() {
//    val chord = dataStoreRepo.myChord.asLiveData()
//    fun saveChord(chord:String){
//        viewModelScope.launch(Dispatchers.IO) {
//            dataStoreRepo.saveChord(chord)
//        }
//    }
//}
//
//
//class ChordViewModelFactory(private val dataStoreRepo: ChordRepo): ViewModelProvider.Factory{
//    override fun <T: ViewModel> create(modelClass: Class<T>):T{
//        return ChordViewModel(dataStoreRepo) as T
//    }
//}