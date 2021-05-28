package com.bangkit.scade.ui.hospital.detail_hospital

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.GetDiagnosesEntity
import com.bangkit.scade.data.source.local.entity.HospitalEntity
import com.bangkit.scade.vo.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class DetailHospitalViewModel(val repository: Repository) : ViewModel() {

    private val _dataHospital = MutableLiveData<Resource<HospitalEntity>>()
    var dataHospital: LiveData<Resource<HospitalEntity>> = _dataHospital

    private val _dataDiagnose = MutableLiveData<Resource<GetDiagnosesEntity>>()
    var dataDiagnose: LiveData<Resource<GetDiagnosesEntity>> = _dataDiagnose

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _dataDiagnose.postValue(Resource.error("Something went wrong", null))
        _dataDiagnose.postValue(Resource.error("Something went wrong", null))
    }

    fun getDataHospital(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            _dataHospital.postValue(Resource.loading(null))
            val result = repository.getDetailHospital(id)
            _dataHospital.postValue(Resource.success(result))
        }
    }

    fun getDataDiagnose(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            _dataDiagnose.postValue(Resource.loading(null))
            val result = repository.getDetailDiagnoses(id)
            _dataDiagnose.postValue(Resource.success(result))
        }
    }
}