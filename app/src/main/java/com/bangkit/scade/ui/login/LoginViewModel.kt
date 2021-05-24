package com.bangkit.scade.ui.login
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.remote.response.LoginRequest
import com.bangkit.scade.data.source.remote.response.LoginResponse
import com.bangkit.scade.vo.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private var _login = MutableLiveData<Resource<LoginResponse>>()
    val login = _login

    //exception handler
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _login.postValue(Resource.error("Something went wrong", null))
    }

    //action login
    fun login(loginData: LoginRequest) {
        viewModelScope.launch(exceptionHandler) {
            _login.postValue(Resource.loading(null))
            val result = repository.login(loginData)
            _login.postValue(Resource.success(result))
        }
    }

    //action insert session
    fun insertSession(data: DataEntity) {
        viewModelScope.launch {
            repository.addDataCheck(data)
        }
    }
}