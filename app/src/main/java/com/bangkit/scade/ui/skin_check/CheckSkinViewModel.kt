package com.bangkit.scade.ui.skin_check

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import com.bangkit.scade.vo.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.File

class CheckSkinViewModel(private val repository: Repository): ViewModel() {

    private var _resultCheckSkin = MutableLiveData<Resource<SkinImageResponse>>()
    val resultCheckSkin = _resultCheckSkin

    private val exceptionHandler = CoroutineExceptionHandler{ _, exception ->
        _resultCheckSkin.postValue(Resource.error("Something went wrong", null))
    }


    fun checkSkinCancer(image: File){
        viewModelScope.launch(exceptionHandler) {
            _resultCheckSkin.postValue(Resource.loading(null))
            val result = repository.imageUpload(image)
            _resultCheckSkin.postValue(Resource.success(result))
        }
    }

    

}