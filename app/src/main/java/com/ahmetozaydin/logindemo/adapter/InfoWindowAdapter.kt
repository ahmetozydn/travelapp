package com.ahmetozaydin.logindemo.adapter

import com.ahmetozaydin.logindemo.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker

class InfoWindowAdapter(private val mContext: Context) :
    InfoWindowAdapter {
    private val mWindow: View =
        LayoutInflater.from(mContext).inflate(R.layout.custom_info_window, null)

    private fun rendowWindowText(marker: Marker, view: View) {
        val title = marker.title
        val tvTitle = view.findViewById<View>(R.id.title) as TextView
        tvTitle.text = title
        val snippet = marker.snippet
        if(snippet?.isNotEmpty() == true){
            val tvSnippet = view.findViewById<View>(R.id.snippet) as TextView
            tvSnippet.text = snippet
        }
    }

    override fun getInfoWindow(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}