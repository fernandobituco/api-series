package com.audax.projetoseries.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.audax.projetoseries.R
import com.audax.projetoseries.managers.UserManagement
import com.audax.projetoseries.adapters.SeriesAdapter
import com.audax.projetoseries.interfaces.RetrofitInstance
import com.audax.projetoseries.model.Serie
import com.audax.projetoseries.model.SerieResponse
import com.audax.projetoseries.model.SpacesItemDecoration
import java.io.Serializable

class MainScreen : AppCompatActivity() {
    companion object {
        const val TAG = "MainScreen"
    }
    var series: List<SerieResponse> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        series = listSeries()

        val newSeriesButton = findViewById<ImageView>(R.id.newSeriesButton)
        newSeriesButton.setOnClickListener{goToCreate()}

        val searchButton = findViewById<ImageView>(R.id.searchButton)
        searchButton.setOnClickListener{goToSearch(series)}

        val returnButton = findViewById<ImageView>(R.id.returnButton)
        returnButton.setOnClickListener{this.finish()}
    }

    private fun listSeries(): List<SerieResponse> {
        val accessToken = getSharedPreferences("accessToken", Context.MODE_PRIVATE).getString("accessToken", null)
        lifecycleScope.launchWhenCreated {
            series = RetrofitInstance.api.getSeries("Bearer $accessToken").body()?.data!!
            Log.d(TAG, series.toString())
            val adapter = series.let { SeriesAdapter(this@MainScreen, it) }
            val recyclerView = findViewById<RecyclerView>(R.id.rvCapas)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(this@MainScreen, 3)
            recyclerView.addItemDecoration(SpacesItemDecoration())
            adapter.setOnItemClickListener(object : SeriesAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@MainScreen, SeriesActivity::class.java)
                    val bundle: Bundle
                    intent.putExtra("Serie", series.elementAt(position))
                    startActivity(intent)
                }
            })
        }
        return series
    }

    override fun onResume() {
        super.onResume()

        series = listSeries()

    }

    private fun goToCreate() {
        val intent = Intent(this@MainScreen, CreateActivity::class.java)
        startActivity(intent)
    }

    private fun goToSearch(series: List<SerieResponse>) {
        val intent = Intent(this@MainScreen,SearchActivity::class.java)
        val bundle: Bundle
        intent.putExtra("Series", series as Serializable)
        startActivity(intent)
    }
}