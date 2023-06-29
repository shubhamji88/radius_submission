package com.example.radius.data.entity


import android.util.Log
import com.example.radius.data.model.ExclusionListModel
import com.example.radius.data.model.ExclusionModel
import com.example.radius.data.model.FacilityModel
import com.example.radius.data.model.RadiusResponseModel
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.kotlin.ext.realmListOf

data class RadiusResponseEntity(
    @SerializedName("exclusions")
    val exclusionsEntity: List<List<ExclusionEntity?>?>,
    @SerializedName("facilities")
    val facilitiesEntity: List<FacilityEntity>
)

fun RadiusResponseEntity.convertToModel(): RadiusResponseModel {
    val convertedExclusionsList = exclusionsEntity.map { exclusionList ->
        ExclusionListModel().apply {
            this.exclusionList = RealmList<ExclusionModel>().apply {
                exclusionList?.map { exclusion ->
                    ExclusionModel().apply {
                        this.facilityId =exclusion?.facilityIdEntity ?: ""
                        this.optionsId=exclusion?.optionsIdEntity ?: ""
                    }
                } ?: emptyList()
            }
        }
    }
    val convertedExclusionsRealmList = RealmList<ExclusionListModel>().apply{
        addAll(
            convertedExclusionsList
        )
    }
    val convertedFacilities = facilitiesEntity.map { facility ->
        facility.convertToModel()
    }
    val convertedFacilityModelRealmList = RealmList<FacilityModel>().apply {
        addAll(convertedFacilities)
    }

    return RadiusResponseModel().apply {
        this.exclusions = convertedExclusionsRealmList
        this.facilities = convertedFacilityModelRealmList
    }

}
