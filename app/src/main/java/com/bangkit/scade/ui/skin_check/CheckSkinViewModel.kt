package com.bangkit.scade.ui.skin_check

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.remote.response.SkinImageResponse
import kotlinx.coroutines.launch
import java.io.File

class CheckSkinViewModel(val repository: Repository): ViewModel() {

    private var _resultCheckSkin = MutableLiveData<SkinImageResponse>()
    val resultCheckSkin = _resultCheckSkin

    fun checkSkinCancer(image: File){
        viewModelScope.launch {
            val result = repository.imageUpload(image)
            _resultCheckSkin.value = result
        }
    }
}