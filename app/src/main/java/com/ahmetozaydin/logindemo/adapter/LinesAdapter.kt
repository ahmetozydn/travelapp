package com.ahmetozaydin.logindemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.logindemo.databinding.RcyclerViewStopsBinding
import com.ahmetozaydin.logindemo.model.Point

class LinesAdapter(val stopList: List<Point>) : RecyclerView.Adapter<LinesAdapter.PlaceHolder>() {//girdi olarak bir hat listesi alması gerekebilir.


    class PlaceHolder( val binding: RcyclerViewStopsBinding ): RecyclerView.ViewHolder(binding.root){



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {// layout ile bağlama işlemi, view binding ile
        val binding = RcyclerViewStopsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlaceHolder(binding)

    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {//bağlamadan sonraki kısım
        holder.binding.latitude.text =""

        holder.itemView.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return stopList.size
    }


}