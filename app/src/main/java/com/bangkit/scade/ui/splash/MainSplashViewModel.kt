package com.bangkit.scade.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.remote.response.SessionResponse
import kotlinx.coroutines.launch

class MainSplashViewModel(val repository: Repository): ViewModel() {

    private var _exist = MutableLiveData<Boolean>()
    val exist = _exist

    private var _dataSession = MutableLiveData<DataEntity>()
    val dataSession = _dataSession

    private var _session = MutableLiveData<SessionResponse>()
    val session = _session

    init {
        checkExist()
    }

    fun setDataSession() {
        viewModelScope.launch {
            val result = repository.getDataCheck()
            _dataSession.postValue(result.value)
        }
    }

    fun checkSession(token: String) {
        viewModelScope.launch {
            val result = repository.checkSession(token)
            _session.postValue(result)
        }

    }

    private fun checkExist() {
        val result = repository.checkDataExist(1)
        _exist.value = result
    }


}