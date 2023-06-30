package com.example.radius.screens

import android.util.Log
import com.example.radius.data.entity.RadiusResponseEntity
import com.example.radius.data.entity.convertToModel
import com.example.radius.data.model.RadiusResponseModel
import com.example.radius.network.RadiusApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm

class Model(private val radiusApi: RadiusApi) : Contract.Model {
    override fun fetchFromApi(): Observable<RadiusResponseEntity> {
        return radiusApi.getHomeData()
            .subscribeOn(Schedulers.io())
    }

    override fun updateRadiusResponseInDatabase(
        radiusResponse: RadiusResponseModel
    ) {
        clearDatabase()
            .andThen(
                Completable.fromAction {
                    Realm.getDefaultInstance().use { realm ->
                        realm.executeTransactionAsync { r ->
                            r.insertOrUpdate(radiusResponse)
                        }
                    }
                }
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Database", "Database updated!!")
            }, { error ->
                Log.d("Database", error.message.toString())
            })
    }


    override fun getLatestRadiusResponseFromDatabase(): Observable<RadiusResponseModel> {
        return Observable.fromCallable {
            Realm.getDefaultInstance().use { realm ->
                realm.where(RadiusResponseModel::class.java).findFirst()?.let { data ->
                    realm.copyFromRealm(data)
                } ?: RadiusResponseModel()
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun clearDatabase(): Completable {
        return Completable.fromAction {
            Realm.getDefaultInstance().use { realm ->
                realm.executeTransaction { transaction ->
                    transaction.deleteAll()
                }
            }
        }.subscribeOn(Schedulers.io())
    }
}