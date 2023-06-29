package com.example.radius.data.entity


import com.example.radius.data.model.ExclusionModel
import com.google.gson.annotations.SerializedName

data class ExclusionEntity(
    @SerializedName("facility_id")
    val facilityIdEntity: String,
    @SerializedName("options_id")
    val optionsIdEntity: String
)

fun ExclusionEntity.convertToModel() =
    ExclusionModel().apply {
        this.facilityId = facilityIdEntity
        this.optionsId = optionsIdEntity
    }


