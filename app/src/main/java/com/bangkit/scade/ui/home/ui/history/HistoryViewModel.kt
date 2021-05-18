package com.bangkit.scade.ui.home.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Your Last Ceck Up"
    }
    val text: LiveData<String> = _text
}