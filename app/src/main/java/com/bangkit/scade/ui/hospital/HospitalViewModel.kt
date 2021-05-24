package com.bangkit.scade.ui.hospital

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.HospitalEntity
import kotlinx.coroutines.launch

class HospitalViewModel(val repository: Repository) : ViewModel() {

    private val _listHospital = MutableLiveData<List<HospitalEntity>>().apply {
        viewModelScope.launch {
            value = repository.getListHospital()
        }
    }

    var listHospital: LiveData<List<HospitalEntity>> = _listHospital
}