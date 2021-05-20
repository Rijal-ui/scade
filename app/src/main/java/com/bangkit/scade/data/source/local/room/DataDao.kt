package com.bangkit.scade.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.scade.data.source.local.entity.DataEntity

@Dao
interface DataDao {

    @Query("SELECT * FROM session")
    fun getDataCheck(): LiveData<DataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DataEntity)

    @Delete
    fun delete(data: DataEntity)

    @Update
    fun update(data: DataEntity)
}