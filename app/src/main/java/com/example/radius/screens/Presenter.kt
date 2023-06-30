package com.example.radius.screens

import com.example.radius.data.entity.RadiusResponseEntity
import com.example.radius.data.entity.convertToModel
import com.example.radius.data.model.ExclusionListModel
import com.example.radius.data.model.RadiusResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.realm.RealmList

class Presenter(private val model: Contract.Model, private val view: Contract.View) : Contract.Presenter {

    private var exclusionList= mutableListOf<MutableList<Pair<String,String>>>()
    private var currentlyChecked = mutableListOf<Pair<String,String>>()


    fun checkForExclusion(facilityId:String , optionId:String):Boolean{
        val selectedPair = Pair(facilityId, optionId)
        val foundInExclusion: List<Pair<String, String>> = exclusionList.find { it.contains(selectedPair) } ?: listOf<Pair<String, String>>()
        if(foundInExclusion.isNotEmpty()){
            val tempToStoreOnlyLeft = foundInExclusion.toMutableList()
            tempToStoreOnlyLeft.remove(selectedPair)
            val tempResult= !currentlyChecked.containsAll(tempToStoreOnlyLeft)
            if(tempResult) {
                currentlyChecked.add(selectedPair)
            }
            return tempResult;
        }

        return true
    }
    fun unCheckOptionItem(facilityId:String , optionId:String){
        val pairToRemove = Pair(facilityId, optionId)
        currentlyChecked.remove(pairToRemove)
    }
    private fun setExclusionList(exclusions: RealmList<ExclusionListModel>) {
        exclusions.forEach{ exclusion ->
            val tempList = mutableListOf<Pair<String, String>>()
            exclusion.exclusionList.toList().forEach { it->
                tempList.add(Pair(it.facilityId,it.optionsId))
            }
            exclusionList.add(tempList)
        }
    }
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
                if(response is RadiusResponseEntity){
                    /// This is when the data is coming from API
                    val response = response.convertToModel();
                    model.updateRadiusResponseInDatabase(response)
                    view.showData(response)
                    val list = response.exclusions[0]?.exclusionList
                    setExclusionList(response.exclusions)
                }else{
                    // When we are getting cached data
                    view.showData(response as RadiusResponseModel)
                    val list = response.exclusions[0]?.exclusionList
                    setExclusionList(response.exclusions)

                    view.hideProgressBar()
                }
            }, { error ->
                view.hideProgressBar()
                view.showError(error.message)
            })
    }

}
