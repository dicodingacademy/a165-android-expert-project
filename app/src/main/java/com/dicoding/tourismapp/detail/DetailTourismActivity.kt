package com.dicoding.tourismapp.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.tourismapp.MyApplication
import com.dicoding.tourismapp.R
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.core.ui.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_tourism.*
import kotlinx.android.synthetic.main.content_detail_tourism.*
import javax.inject.Inject

class DetailTourismActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val detailTourismViewModel: DetailTourismViewModel by viewModels {
        factory
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tourism)
        setSupportActionBar(toolbar)

//        hapus kode berikut
//        val factory = ViewModelFactory.getInstance(this)
//        detailTourismViewModel = ViewModelProvider(this, factory)[DetailTourismViewModel::class.java]

        val detailTourism = intent.getParcelableExtra<Tourism>(EXTRA_DATA)
        showDetailTourism(detailTourism)
    }

    private fun showDetailTourism(detailTourism: Tourism?) {
        detailTourism?.let {
            supportActionBar?.title = detailTourism.name
            tv_detail_description.text = detailTourism.description
            Glide.with(this@DetailTourismActivity)
                .load(detailTourism.image)
                .into(text_detail_image)

            var statusFavorite = detailTourism.isFavorite
            setStatusFavorite(statusFavorite)
            fab.setOnClickListener {
                statusFavorite = !statusFavorite
                detailTourismViewModel.setFavoriteTourism(detailTourism, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
        }
    }
}
