package com.example.radius.data.model


import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class OptionModel() : RealmModel {
    var icon: String = ""
    var id: String = ""
    var name: String = ""
}