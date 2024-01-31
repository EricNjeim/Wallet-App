package com.example.eric_summer2023

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eric_summer2023.data.repository.Myrepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CoroutineViewModel @Inject constructor(private val repos: Myrepository) : ViewModel() {
    private var _balance = MutableLiveData<Double?>()
    val balance: LiveData<Double?> = _balance
    private var _fullName = MutableLiveData<String?>()
    val fullName: LiveData<String?> = _fullName
    fun performAsyncOperation() {
        viewModelScope.launch {
            try {
                val result = repos.dofirestorecall()
                _balance.postValue(result.first)
                Log.e(ContentValues.TAG,result.second)
                    _fullName.postValue(result.second)
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Firestore error: ${e.message}", e)
            }
        }
    }
}