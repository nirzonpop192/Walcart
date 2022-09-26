package com.example.rocketreserver

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()






    val categorieList = MutableLiveData<List<GetCategoriesListQuery.Category>?>()







    val loading = MutableLiveData<Boolean>()

    fun getAllCategories() {
        Log.d("Thread Outside", Thread.currentThread().name)

        viewModelScope.launch {
            Log.d("Thread Inside", Thread.currentThread().name)
          val response = mainRepository.getAllMovies()

                    categorieList.postValue(response)


            }
        }






}

