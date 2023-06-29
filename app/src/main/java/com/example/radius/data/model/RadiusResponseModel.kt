package com.example.radius.data.model


import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class RadiusResponseModel() : RealmModel {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var exclusions: RealmList<ExclusionListModel> = RealmList<ExclusionListModel>()
    var facilities: RealmList<FacilityModel> = RealmList<FacilityModel>()
}
