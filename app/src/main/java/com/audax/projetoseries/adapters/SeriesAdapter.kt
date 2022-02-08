package com.audax.projetoseries.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.audax.projetoseries.R
import com.audax.projetoseries.model.Serie
import com.audax.projetoseries.model.SerieResponse
import com.bumptech.glide.Glide

class SeriesAdapter (val context: Context, val series: List<SerieResponse>) :
RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    private lateinit var mListener: onItemClickListener

    fun setOnItemClickListener (listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_serie, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun getItemCount() = series.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(series[position])
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        fun bind(serie: SerieResponse) {
            Glide.with(context).load(serie.capa).into(itemView.findViewById<ImageView>(R.id.imageViewCapa))
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
