package com.example.radius.data.model

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class FacilityModel() : RealmModel {
    var facilityId: String = ""
    var name: String = ""
    var options: RealmList<OptionModel> = RealmList<OptionModel>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FacilityModel) return false

        if (facilityId != other.facilityId) return false
        if (name != other.name) return false
        if (options != other.options) return false

        return true
    }
}