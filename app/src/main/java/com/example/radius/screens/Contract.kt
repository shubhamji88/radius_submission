package com.example.radius.screens

import com.example.radius.data.entity.RadiusResponseEntity
import com.example.radius.data.model.RadiusResponseModel
import com.example.radius.network.RadiusApi
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.lang.Error

interface Contract {
    interface Model{
        fun fetchFromApi(): Observable<RadiusResponseEntity>
        fun updateRadiusResponseInDatabase(radiusResponse: RadiusResponseModel)
        fun getLatestRadiusResponseFromDatabase(): Observable<RadiusResponseModel>
        fun clearDatabase() : Completable
    }
    interface View{
        fun hideProgressBar()
        fun showProgressBar()
        fun showError(error: String?)
        fun showData(data: RadiusResponseModel)
    }
    interface Presenter{
        fun getData()
    }
}