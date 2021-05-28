package com.bangkit.scade.ui.skin_check

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.local.entity.DiagnosesEntity
import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import com.bangkit.scade.vo.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.File

class CheckSkinViewModel(private val repository: Repository) : ViewModel() {

    private var _resultCheckSkin = MutableLiveData<Resource<SkinImageResponse>>()
    val resultCheckSkin = _resultCheckSkin

    private var _idDiagnose = MutableLiveData<Resource<DiagnosesEntity>>()
    val idDiagonse = _idDiagnose

    private var _session = MutableLiveData<String>()
    val session = _session

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _resultCheckSkin.postValue(Resource.error("Something went wrong", null))
        _idDiagnose.postValue(Resource.error("Something went wrong", null))

    }

    fun getSession(): LiveData<DataEntity> {
        return repository.getSessionToken()
    }


    fun checkSkinCancer(image: File) {
        viewModelScope.launch(exceptionHandler) {
            _resultCheckSkin.postValue(Resource.loading(null))
            val result = repository.imageUpload(image)
            _resultCheckSkin.postValue(Resource.success(result))
        }
    }

    fun createDiagnoses(token: String, cancerName: String, image: File, position: String) {
        viewModelScope.launch(exceptionHandler) {
            _idDiagnose.postValue(Resource.loading(null))
            val result = repository.createDiagnoses(token, cancerName, image, position)
            Log.d("checkcreatediag", result.data.toString())
            _idDiagnose.postValue(Resource.success(result))
        }
    }


}