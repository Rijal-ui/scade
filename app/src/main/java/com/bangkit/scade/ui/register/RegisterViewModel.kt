package com.bangkit.scade.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.remote.response.RegisterRequest
import com.bangkit.scade.data.source.remote.response.RegisterResponse
import com.bangkit.scade.vo.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: Repository): ViewModel() {

    private var _register = MutableLiveData<Resource<RegisterResponse>>()
    val register = _register

    private val exceptionHandler = CoroutineExceptionHandler{ _, exception ->
        _register.postValue(Resource.error("Something went wrong", null))
    }

    fun register(registerData: RegisterRequest) {
        viewModelScope.launch(exceptionHandler) {
            _register.postValue(Resource.loading(null))
            val result = repository.register(registerData)
            _register.postValue(Resource.success(result))
        }
    }

}