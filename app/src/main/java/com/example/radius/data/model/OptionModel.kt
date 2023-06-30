package com.example.radius.data.model


import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class OptionModel() : RealmModel {
    var icon: String = ""
    var id: String = ""
    var name: String = ""
    var selected = false
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OptionModel) return false

        if (icon != other.icon) return false
        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

}