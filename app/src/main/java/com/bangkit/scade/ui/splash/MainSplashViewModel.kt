package com.bangkit.scade.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.remote.response.SessionResponse
import com.bangkit.scade.vo.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainSplashViewModel(val repository: Repository) : ViewModel() {
    private var _dataSession = MutableLiveData<Resource<DataEntity>>()
    val dataSession = _dataSession

    private var _session = MutableLiveData<Resource<SessionResponse>>()
    val session = _session

    //exception handler
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _session.postValue(Resource.error("Something went wrong", null))
    }


    fun setDataSession(): LiveData<DataEntity> {
        return repository.getDataCheck()
    }

    fun checkSession(token: String) {
        viewModelScope.launch(exceptionHandler) {
            _session.postValue(Resource.loading(null))
            val result = repository.checkSession(token)
            _session.postValue(Resource.success(result))
        }
    }

    fun checkExist(): LiveData<Boolean> {
        return repository.checkDataExist(1)
    }
}