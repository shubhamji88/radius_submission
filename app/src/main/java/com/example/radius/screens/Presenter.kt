package com.example.radius.screens

import com.example.radius.data.entity.RadiusResponseEntity
import com.example.radius.data.entity.convertToModel
import com.example.radius.data.model.FacilityModel
import com.example.radius.data.model.RadiusResponseModel
import com.example.radius.network.RadiusApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.realm.RealmList

class Presenter(private val model: Contract.Model, private val view: Contract.View) : Contract.Presenter {


    override fun getData() {
        view.showProgressBar()

        model.getLatestRadiusResponseFromDatabase()
            .flatMap { cachedResponse ->
                if (cachedResponse.facilities.size !=0) {
                    Observable.just(cachedResponse)
                } else {
                    model.fetchFromApi()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                view.hideProgressBar()
                if(response is RadiusResponseEntity){
                    /// This is when the data is coming from API
                    val response = response.convertToModel();
                    model.updateRadiusResponseInDatabase(response)
                    view.showData(response)
                }else{
                    // When we are getting cached data
                    view.showData(response as RadiusResponseModel)
                }
            }, { error ->
                view.hideProgressBar()
                view.showError(error.message)
            })
    }

}
