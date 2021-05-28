package com.bangkit.scade.ui.hospital

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.HospitalEntity
import com.bangkit.scade.vo.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class HospitalViewModel(val repository: Repository) : ViewModel() {

    private val _listHospital = MutableLiveData<Resource<List<HospitalEntity>>>()

    private val exceptionHandler = CoroutineExceptionHandler{ _, exception ->
        _listHospital.postValue(Resource.error("Something went wrong", null))
    }

    var listHospital: LiveData<Resource<List<HospitalEntity>>> = _listHospital

    fun setListHospital() {
        viewModelScope.launch(exceptionHandler) {
            _listHospital.postValue(Resource.loading(null))
            val result = repository.getListHospital()
            _listHospital.postValue(Resource.success(result))
        }
    }

    fun setSearchHospital(search: String) {
        viewModelScope.launch(exceptionHandler) {
            _listHospital.postValue(Resource.loading(null))
            val result = repository.getSearchHospital(search)
            _listHospital.postValue(Resource.success(result))
        }
    }
}