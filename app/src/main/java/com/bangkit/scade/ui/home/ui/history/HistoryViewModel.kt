package com.bangkit.scade.ui.home.ui.history

import android.content.res.loader.ResourcesProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()

    val text: LiveData<String> = _text
}