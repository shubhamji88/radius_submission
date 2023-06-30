package com.example.radius.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.radius.R
import com.example.radius.data.model.RadiusResponseModel
import com.example.radius.databinding.ActivityMainBinding
import com.example.radius.network.RadiusApi
import com.example.radius.screens.adapter.FacilityAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Contract.View {
    @Inject
    lateinit var radiusApi: RadiusApi
    lateinit var presenter: Presenter
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewAdapter: FacilityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initializeUI()
        presenter = Presenter(
            Model(radiusApi),
            this
        )
        presenter.getData()

    }

    private fun initializeUI() {
        recyclerViewAdapter = FacilityAdapter({ facilityId, optionId ->
            presenter.checkForExclusion(facilityId, optionId)
        }, { facilityId, optionId ->
            presenter.unCheckOptionItem(facilityId, optionId)
        })
        binding.mainRv.adapter = recyclerViewAdapter
    }

    override fun hideProgressBar() {
        binding.progress.visibility = View.GONE
    }

    override fun showProgressBar() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun showError(error: String?) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

    override fun showData(data: RadiusResponseModel) {
        recyclerViewAdapter.submitList(data.facilities.toList())
    }


}