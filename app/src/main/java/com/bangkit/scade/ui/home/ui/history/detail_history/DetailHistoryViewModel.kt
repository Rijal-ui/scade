package com.bangkit.scade.ui.home.ui.history.detail_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.local.entity.InvoicesEntity
import com.bangkit.scade.vo.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class DetailHistoryViewModel(private val repository: Repository) : ViewModel() {

    private val _dataHistory = MutableLiveData<Resource<InvoicesEntity>>()
    var dataHistory = _dataHistory

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _dataHistory.postValue(Resource.error("Something went wrong", null))
    }

    fun getSession(): LiveData<DataEntity> {
        return repository.getSessionToken()
    }

    fun getDataHistory(token: String, id: Int) {
        viewModelScope.launch(exceptionHandler) {
            _dataHistory.postValue(Resource.loading(null))
            val result = repository.getDetailInvoices(token, id)
            _dataHistory.postValue(Resource.success(result))
        }
    }
}