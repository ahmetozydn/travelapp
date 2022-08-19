package com.ahmetozaydin.logindemo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.logindemo.LinesFragment
import com.ahmetozaydin.logindemo.databinding.RcyclerViewStopsBinding
import com.ahmetozaydin.logindemo.model.Point
import com.ahmetozaydin.logindemo.model.ServiceModel
import com.ahmetozaydin.logindemo.model.Services


class LinesAdapter(private val serviceList: List<Services>) : RecyclerView.Adapter<LinesAdapter.PlaceHolder>() {//girdi olarak bir hat listesi alması gerekebilir.

    interface Listener{
        fun onItemClick(services:Services)//service : Service de alabilir.


    }

    class PlaceHolder( val binding: RcyclerViewStopsBinding ): RecyclerView.ViewHolder(binding.root){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {// layout ile bağlama işlemi, view binding ile
        val binding = RcyclerViewStopsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlaceHolder(binding)

    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {//bağlamadan sonraki kısım,hangi item ne verisi gösterecek.
       // holder.binding.lineName.text = serviceList[position].name.toString()


        holder.binding.description.text ="  ${serviceList[position].name.toString()}      ${serviceList[position].description.toString()}"//Alp'e buranın designının nasıl daha iyi olması gerektiğini sor.



        holder.itemView.setOnClickListener{


        }


    }

    override fun getItemCount(): Int {
        return serviceList.count() //or serviceList.size
    }




}