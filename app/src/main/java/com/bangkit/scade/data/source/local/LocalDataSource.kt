package com.bangkit.scade.data.source.local

import androidx.lifecycle.LiveData
import com.bangkit.scade.data.source.local.entity.DataEntity
import com.bangkit.scade.data.source.local.room.DataDao

class LocalDataSource private constructor(private val dataDao: DataDao){

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(dataDao: DataDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(dataDao)
    }

    fun getDataCheck(): LiveData<DataEntity> = dataDao.getDataCheck()

    fun insertDataCheck(data: DataEntity) {
        dataDao.insert(data)
    }

    fun deleteDataCheck(data: DataEntity) {
        dataDao.delete(data)
    }
}