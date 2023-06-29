package com.example.radius.network

import com.example.radius.model.RadiusResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface RadiusApi {
    @GET("iranjith4/ad-assignment/db")
    fun getHomeData():Observable<RadiusResponse>
}