package com.example.radius.data.model

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class FacilityModel() : RealmModel {
    var facilityId: String = ""
    var name: String = ""
    var options: RealmList<OptionModel> = RealmList<OptionModel>()
}