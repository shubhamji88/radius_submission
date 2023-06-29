package com.example

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class RadiusApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        Realm.init(this);

        val configuration = RealmConfiguration.Builder()
            .name("todo.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowQueriesOnUiThread(true)
            .build()

        Realm.setDefaultConfiguration(configuration)
    }
}