package com.audax.projetoseries.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.audax.projetoseries.R
import com.audax.projetoseries.interfaces.RetrofitInstance
import com.audax.projetoseries.model.Serie
import com.audax.projetoseries.model.SerieResponse

class SeriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series)

        val accessToken = getSharedPreferences("accessToken", Context.MODE_PRIVATE).getString("accessToken", null)

        val serie: SerieResponse = intent.getSerializableExtra("Serie") as SerieResponse

        val returnButton = findViewById<ImageView>(R.id.returnFromSeriesButton)
        returnButton.setOnClickListener{this.finish()}

        val title = findViewById<TextView>(R.id.seriesTitle)
        title.text = serie.nome

        val sinopse = findViewById<TextView>(R.id.seriesSinopse)
        sinopse.text = serie.descricao

        val deleteButton = findViewById<TextView>(R.id.deleteButton)
        deleteButton.setOnClickListener { lifecycleScope.launchWhenCreated {
            RetrofitInstance.api.deleteSerie("Bearer $accessToken", serie.id) }
            this.finish()
        }
    }
}