package com.audax.projetoseries.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.audax.projetoseries.R
import com.audax.projetoseries.adapters.SeriesAdapter
import com.audax.projetoseries.model.Serie
import com.audax.projetoseries.model.SerieResponse
import com.audax.projetoseries.model.SpacesItemDecoration

class SearchActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SearchActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val series: MutableList<SerieResponse> = intent.getSerializableExtra("Series") as MutableList<SerieResponse>
        Log.d("TAG", series.toString())
        val seriesNames = mutableListOf<SerieResponse>()
        seriesNames.addAll(series)
        Log.d("TAG", seriesNames.toString())

        val adapter = seriesNames.let { SeriesAdapter(this@SearchActivity, it) }
        val recyclerView = findViewById<RecyclerView>(R.id.rvSearch)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this@SearchActivity, 3)
        recyclerView.addItemDecoration(SpacesItemDecoration())
        adapter.setOnItemClickListener(object : SeriesAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@SearchActivity,SeriesActivity::class.java)
                val bundle: Bundle
                intent.putExtra("Serie", seriesNames.elementAt(position))
                startActivity(intent)
            }

        })

        val serieName = findViewById<EditText>(R.id.serieName)

        val searchName = findViewById<ImageView>(R.id.searchNameButton)
        searchName.setOnClickListener{searchName(series, serieName.text.toString(), seriesNames, adapter)}

        val returnButton = findViewById<ImageView>(R.id.returnFromSearchButton)
        returnButton.setOnClickListener{this.finish()}
    }

    private fun searchName(series: List<SerieResponse>, serieName: String, seriesNames: MutableList<SerieResponse>, adapter: SeriesAdapter) {
        seriesNames.clear()
        series.forEach {
            if(serieName in it.nome || it.nome in serieName) {
                seriesNames.add(it)
            }
        }
        adapter.notifyDataSetChanged()
    }
}