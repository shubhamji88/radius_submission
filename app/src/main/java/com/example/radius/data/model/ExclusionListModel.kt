package com.example.radius.data.model

import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class ExclusionListModel() : RealmModel {
    var exclusionList: RealmList<ExclusionModel> = RealmList<ExclusionModel>()
}