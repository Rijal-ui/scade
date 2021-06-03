package com.bangkit.scade.ui.home.ui.history

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

class HistoryViewModel(val repository: Repository) : ViewModel() {

    private val _listHistory = MutableLiveData<Resource<List<InvoicesEntity>>>()
    var listHistory = _listHistory

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _listHistory.postValue(Resource.error("Something went wrong", null))
    }

    fun getSession(): LiveData<DataEntity> {
        return repository.getSessionToken()
    }

    fun setListHistory(token: String) {
        viewModelScope.launch(exceptionHandler) {
            _listHistory.postValue(Resource.loading(null))
            val result = repository.getListInvoices(token)
            _listHistory.postValue(Resource.success(result))
        }
    }
}