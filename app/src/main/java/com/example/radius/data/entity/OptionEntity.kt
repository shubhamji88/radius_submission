package com.example.radius.data.entity


import com.example.radius.data.model.OptionModel
import com.google.gson.annotations.SerializedName

data class OptionEntity(
    @SerializedName("icon")
    val iconEntity: String,
    @SerializedName("id")
    val idEntity: String,
    @SerializedName("name")
    val nameEntity: String
)

fun OptionEntity.convertToModel() =
    OptionModel().apply {
        this.id = idEntity
        this.icon = iconEntity
        this.name = nameEntity
    }