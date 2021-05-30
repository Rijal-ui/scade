package com.bangkit.scade.ui.hospital.detail_hospital

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.local.entity.HospitalEntity
import com.bangkit.scade.data.source.local.entity.InvoicesEntity
import com.bangkit.scade.data.source.remote.request.UpdateHospitalRequest
import com.bangkit.scade.data.source.remote.response.UpdateHospitalResponse
import com.bangkit.scade.vo.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class UpdateBookingHospitalViewModel(val repository: Repository) : ViewModel() {

    private val _invoice = MutableLiveData<Resource<InvoicesEntity>>()
    var invoice = _invoice

    private val _update = MutableLiveData<Resource<UpdateHospitalResponse>>()
    var update = _update

    private val _dataHospital = MutableLiveData<Resource<HospitalEntity>>()
    var dataHospital = _dataHospital

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _invoice.postValue(Resource.error("Something went wrong", null))
        _update.postValue(Resource.error("Something went wrong", null))
        _dataHospital.postValue(Resource.error("Something went wrong", null))

    }

    fun getSession(): LiveData<DataEntity> {
        return repository.getSessionToken()
    }

    fun getDataHistory(token: String, id: Int) {
        viewModelScope.launch(exceptionHandler) {
            _invoice.postValue(Resource.loading(null))
            val result = repository.getDetailInvoices(token, id)
            _invoice.postValue(Resource.success(result))
        }
    }

    fun updateHospital(token: String, updateData: UpdateHospitalRequest, id: Int) {
        viewModelScope.launch(exceptionHandler) {
            _update.postValue(Resource.loading(null))
            val result = repository.updateHospitalInvoice(token, updateData, id)
            _update.postValue(Resource.success(result))
        }
    }

    fun getDataHospital(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            _dataHospital.postValue(Resource.loading(null))
            val result = repository.getDetailHospital(id)
            _dataHospital.postValue(Resource.success(result))
        }
    }

}