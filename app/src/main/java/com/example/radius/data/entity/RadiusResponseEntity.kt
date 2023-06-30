package com.example.radius.data.entity


import android.util.Log
import com.example.radius.data.model.ExclusionListModel
import com.example.radius.data.model.ExclusionModel
import com.example.radius.data.model.FacilityModel
import com.example.radius.data.model.RadiusResponseModel
import com.google.gson.annotations.SerializedName
import io.realm.RealmList

data class RadiusResponseEntity(
    @SerializedName("exclusions")
    val exclusionsEntity: List<List<ExclusionEntity?>?>,
    @SerializedName("facilities")
    val facilitiesEntity: List<FacilityEntity>
)

fun RadiusResponseEntity.convertToModel(): RadiusResponseModel {
    val convertedExclusionsRealmList = RealmList<ExclusionListModel>()
    exclusionsEntity.forEach { exclusionList ->
        val temp = ExclusionListModel().apply {
            var tempList = RealmList<ExclusionModel>()
            exclusionList?.forEach { entity ->
                val tempEntity = ExclusionModel().apply {
                        this.facilityId =entity?.facilityIdEntity ?: ""
                        this.optionsId=entity?.optionsIdEntity ?: ""
                    }
                tempList.add(tempEntity)
            }

            this.exclusionList =tempList
            }
        convertedExclusionsRealmList.add(temp)
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
