package com.example.radius

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.radius.data.entity.convertToModel
import com.example.radius.data.model.RadiusResponseModel
import com.example.radius.network.RadiusApi
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var realm: Realm = Realm.getDefaultInstance()
    @Inject
    lateinit var radiusApi: RadiusApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radiusApi.getHomeData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                Toast.makeText(
                    applicationContext,
                    response.convertToModel().toString(),
                    Toast.LENGTH_LONG
                ).show()
                addToDB(response.convertToModel())
            }, { error ->
                Toast.makeText(
                    applicationContext,
                    "Error occurred: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            })


    }

    private fun addToDB(convertToModel: RadiusResponseModel) {
        Observable.fromCallable<Unit> {
            Realm.getDefaultInstance().use { realm ->
                realm.executeTransaction { r ->
                    r.insertOrUpdate(convertToModel)
                }
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getData()
            }, { error ->
                Log.d("Database", error.message.toString())
                Toast.makeText(
                    applicationContext,
                    "Error occurred: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            })
    }

    private fun getData() {
        Observable.fromCallable<String> {
            Realm.getDefaultInstance().use { realm ->
                val data = realm.where(RadiusResponseModel::class.java)
                    .findFirst()?.facilities?.get(1)?.name ?: ""
                data
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ data ->
                println("Fetched data: $data")
            }, { error ->
                println("Error occurred: ${error.message}")
            })
    }

}