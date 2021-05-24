package com.bangkit.scade.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.scade.data.source.Repository
import com.bangkit.scade.di.Injection
import com.bangkit.scade.ui.home.ui.home.HomeViewModel
import com.bangkit.scade.ui.home.ui.information.InformationViewModel
import com.bangkit.scade.ui.skin_check.CheckSkinViewModel
import com.bangkit.scade.ui.splash.MainSplashViewModel

class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

        companion object {
            @Volatile
            private var instance: ViewModelFactory? = null

            fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance?: ViewModelFactory(Injection.provideRepository(context))
                }
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel() as T
            }
            modelClass.isAssignableFrom(CheckSkinViewModel::class.java) -> {
                CheckSkinViewModel(repository) as T
            }
            modelClass.isAssignableFrom(InformationViewModel::class.java) -> {
                InformationViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainSplashViewModel::class.java) -> {
                MainSplashViewModel(repository) as T
            }
            else ->throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}