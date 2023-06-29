package com.example.radius.data.model


import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class ExclusionModel() : RealmModel {
    var facilityId: String = ""
    var optionsId: String = ""
}
