package com.bangkit.scade.data.source.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InformationEntity(
    val thumbnail: String? = null,
    val createdAt: String? = null,
    val iD: Int? = null,
    val title: String? = null,
    val body: String? = null,
    val articleLanguageId: Int? = null,
    val updatedAt: String? = null
) : Parcelable