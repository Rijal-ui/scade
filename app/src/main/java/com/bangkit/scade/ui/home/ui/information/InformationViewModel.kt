package com.bangkit.scade.ui.home.ui.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.InformationEntity
import com.bangkit.scade.vo.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.*

class InformationViewModel(val repository: Repository) : ViewModel() {

    private val _listArticle = MutableLiveData<Resource<List<InformationEntity>>>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _listArticle.postValue(Resource.error("Something went wrong", null))
    }

    var listArticle: LiveData<Resource<List<InformationEntity>>> = _listArticle

    fun getListArticle() {
        viewModelScope.launch(exceptionHandler) {
            //English
            if (Locale.getDefault().language == "in") {
                _listArticle.postValue(Resource.loading(null))
                val resultIndo = repository.getListIndoensiaArticle()
                _listArticle.postValue(Resource.success(resultIndo))
            } else {
                _listArticle.postValue(Resource.loading(null))
                val resultEng = repository.getListEnglishArticle()
                _listArticle.postValue(Resource.success(resultEng))
            }
        }
    }

}