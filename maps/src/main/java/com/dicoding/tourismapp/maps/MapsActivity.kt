package com.dicoding.tourismapp.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.dicoding.tourismapp.core.data.Resource
import com.dicoding.tourismapp.maps.di.mapsModule
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class MapsActivity : AppCompatActivity() {

    private val mapsViewModel: MapsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        loadKoinModules(mapsModule)

        supportActionBar?.title = "Tourism Map"

        getTourismData()
    }

    private fun getTourismData() {
        mapsViewModel.tourism.observe(this, Observer { tourism ->
            if (tourism != null) {
                when (tourism) {
                    is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        progress_bar.visibility = View.GONE
                        tv_maps.text = "This is map of ${tourism.data?.get(0)?.name}"
                    }
                    is Resource.Error -> {
                        progress_bar.visibility = View.GONE
                        tv_error.visibility = View.VISIBLE
                        tv_error.text = tourism.message
                    }
                }
            }
        })
    }
}