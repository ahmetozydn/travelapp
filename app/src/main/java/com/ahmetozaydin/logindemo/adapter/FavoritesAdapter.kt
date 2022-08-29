package com.ahmetozaydin.logindemo.adapter

import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.logindemo.databinding.RyclerViewFavoritesBinding

class FavoritesAdapter() : RecyclerView.Adapter<FavoritesAdapter.PlaceHolder>() {
    class PlaceHolder(private val recyclerViewFavoritesBinding: RyclerViewFavoritesBinding ) :RecyclerView.ViewHolder(recyclerViewFavoritesBinding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
      val binding = RyclerViewFavoritesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlaceHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        val query = "SELECT SUM(LENGTH(description) FROM table_description"//tablonun size ının döndürmek için gereken query
        return TODO()
    }
}