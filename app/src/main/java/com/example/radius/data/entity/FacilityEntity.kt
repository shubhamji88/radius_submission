package com.example.radius.data.entity


import android.util.Log
import com.example.radius.data.model.ExclusionListModel
import com.example.radius.data.model.FacilityModel
import com.example.radius.data.model.OptionModel
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.kotlin.ext.realmListOf

data class FacilityEntity(
    @SerializedName("facility_id")
    val facilityIdEntity: String,
    @SerializedName("name")
    val nameEntity: String,
    @SerializedName("options")
    val optionsEntity: List<OptionEntity>
)
fun FacilityEntity.convertToModel() = FacilityModel().apply {
        this.facilityId = facilityIdEntity
        this.name = nameEntity
        this.options = RealmList<OptionModel>().apply {
            val convertedOptions = optionsEntity.map { option ->
                option.convertToModel()
            }
            addAll(convertedOptions)
        }
    Log.d("Convert","convet" + this.facilityId + name )
}

