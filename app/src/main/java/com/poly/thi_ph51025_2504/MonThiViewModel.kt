package com.poly.thi_ph51025_2504

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MonThiViewModel : ViewModel() {
    private val _monThiList = MutableLiveData<List<MonThi>>()

    val MonThi: LiveData<List<MonThi>> = _monThiList

    private val _selectMonThi = MutableLiveData<MonThi?>()

    val SLMonThi: MutableLiveData<MonThi?> = _selectMonThi

    init {
        getXeMay()
    }

    fun getXeMay() {
        viewModelScope.launch {
            try {
                val response = RetrofitService().monThiService.getLit()
                if (response.isSuccessful) {
                    _monThiList.postValue(response.body()?.map { it.toMonThi() })
                } else {
                    _monThiList.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("XeMayViewModel", "getXeMay: $e")
                _monThiList.value = emptyList()
            }
        }
    }

    fun getDetail(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitService().monThiService.getDetail(id.toString())
                if (response.isSuccessful) {
                    _selectMonThi.postValue(response.body()?.toMonThi())
                    Log.e("del", "hehe")
                } else {
                    _selectMonThi.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("del", "loi APi")
            }
        }
    }


    fun clearSelected() {
        _selectMonThi.value = null
    }
}


//b5

