package com.bangkit.scade.ui.home.ui.information

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.data.source.local.entity.InformationEntity
import com.bangkit.scade.data.source.remote.response.ArticlesResponse
import kotlinx.coroutines.launch
import java.util.*

class InformationViewModel(val repository: Repository) : ViewModel() {

    private val _listArticle = MutableLiveData<List<InformationEntity>>().apply {
        viewModelScope.launch {
            //English
            value = if (Locale.getDefault().language == "in") {
                repository.getListIndoensiaArticle()
            } else {
                repository.getListEnglishArticle()
            }
        }
    }
    var listArticle: LiveData<List<InformationEntity>> = _listArticle


}