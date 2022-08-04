package com.ahmetozaydin.logindemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmetozaydin.logindemo.databinding.FragmentFavoritesBinding
import com.ahmetozaydin.logindemo.databinding.FragmentLocationBinding


class FavoritesFragment : Fragment() {

    private lateinit var  binding:FragmentFavoritesBinding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        binding.textView.text =""
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }


}