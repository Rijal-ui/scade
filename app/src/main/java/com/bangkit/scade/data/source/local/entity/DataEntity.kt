package com.bangkit.scade.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session")
data class DataEntity(

    @PrimaryKey
    @ColumnInfo(name = "tokenSession")
    var tokenSession: String
)
