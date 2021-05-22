package com.bangkit.scade.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DataItem(

    @field:SerializedName("thumbnail")
    val thumbnail: String? = null,

    @field:SerializedName("CreatedAt")
    val createdAt: String? = null,

    @field:SerializedName("ID")
    val iD: Int? = null,

    @field:SerializedName("DeletedAt")
    val deletedAt: Any? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("body")
    val body: String? = null,

    @field:SerializedName("article_language_id")
    val articleLanguageId: Int? = null,

    @field:SerializedName("UpdatedAt")
    val updatedAt: String? = null
)

