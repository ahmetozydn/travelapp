package com.ahmetozaydin.logindemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.logindemo.databinding.RyclerViewFavoritesBinding

class FavoritesAdapter(private val databaseList: ArrayList<String>) : RecyclerView.Adapter<FavoritesAdapter.PlaceHolder>() {
    class PlaceHolder(val binding: RyclerViewFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val binding = RyclerViewFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(binding)
    }
    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.binding.eachDescription.text = databaseList[position]
    }
    override fun getItemCount(): Int {
        return databaseList.size
    }
}