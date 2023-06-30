package com.example.radius.screens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.radius.R
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
class MainActivity : AppCompatActivity() , Contract.View {
    @Inject
    lateinit var radiusApi: RadiusApi
    lateinit var presenter : Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = Presenter(
            Model(radiusApi),
            this
        )
        presenter.getData()

     }

    override fun hideProgressBar() {
        Toast.makeText(applicationContext,"Hide progress bar", Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar() {
        Toast.makeText(applicationContext,"showProgressBar", Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: String?) {
        Toast.makeText(applicationContext,"showError" + error, Toast.LENGTH_SHORT).show()
    }

    override fun showData(data: RadiusResponseModel) {
        Toast.makeText(applicationContext,"showData" + data.facilities?.get(1)?.name, Toast.LENGTH_SHORT).show()
    }


}