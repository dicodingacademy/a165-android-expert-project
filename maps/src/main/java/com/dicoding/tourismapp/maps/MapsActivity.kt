package com.dicoding.tourismapp.maps

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.tourismapp.core.data.Resource
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.detail.DetailTourismActivity
import com.dicoding.tourismapp.maps.databinding.ActivityMapsBinding
import com.google.gson.Gson
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.scalebar.scalebar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class MapsActivity : AppCompatActivity() {

    private val mapsViewModel: MapsViewModel by viewModel()
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(mapsModule)
        supportActionBar?.title = "Tourism Map"

        binding.mapView.scalebar.enabled = false
        binding.mapView.mapboxMap.apply {
            loadStyle(style(Style.STANDARD) {
                +projection(ProjectionName.MERCATOR)
            })
        }

        getTourismData()
    }

    private fun getTourismData() {
        mapsViewModel.tourism.observe(this) { tourism ->
            if (tourism != null) {
                when (tourism) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        showMarker(tourism.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = tourism.message
                    }
                }
            }
        }
    }

    private fun showMarker(dataTourism: List<Tourism>?) {
        dataTourism?.forEach { data ->
            val annotationApi = binding.mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            val pointAnnotationOptions =
                PointAnnotationOptions().withPoint(Point.fromLngLat(data.longitude, data.latitude))
                    .withIconImage(BitmapFactory.decodeResource(resources, R.drawable.red_marker))
                    .withIconAnchor(IconAnchor.BOTTOM)
                    .withIconSize(0.3)
                    .withData(Gson().toJsonTree(data))
            pointAnnotationManager.create(pointAnnotationOptions)
            pointAnnotationManager.addClickListener { annotation ->
                val selectedTourism = Gson().fromJson(annotation.getData(), Tourism::class.java)
                val intent = Intent(this, DetailTourismActivity::class.java)
                intent.putExtra(DetailTourismActivity.EXTRA_DATA, selectedTourism)
                startActivity(intent)
                true
            }
        }
    }
}