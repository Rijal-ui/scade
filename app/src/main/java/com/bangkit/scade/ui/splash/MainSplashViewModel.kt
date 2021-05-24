package com.bangkit.scade.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.remote.response.SessionResponse
import kotlinx.coroutines.launch

class MainSplashViewModel(val repository: Repository) : ViewModel() {
    private var _dataSession = MutableLiveData<DataEntity>()
    val dataSession = _dataSession

    private var _session = MutableLiveData<SessionResponse>()
    val session = _session


    fun setDataSession(): LiveData<DataEntity> {
        val result = repository.getDataCheck()
        result.value?.let { Log.d("iniCheckToken", it.tokenSection) }
        return result
//        viewModelScope.launch {
//            val result = repository.getDataCheck()
//            _dataSession.postValue(result.value)
//        }
    }

    fun checkSession(token: String) {
//        var result: SessionResponse = null
//        val postResult = MutableLiveData<SessionResponse?>()
//        viewModelScope.launch {
//            result = repository.checkSession(token)
//        }
//        postResult.value = result
//        return postResult
        viewModelScope.launch {
            val result = repository.checkSession(token)
            _session.postValue(result)
        }
    }

    fun checkExist(): LiveData<Boolean> {
        return repository.checkDataExist(1)
    }
}