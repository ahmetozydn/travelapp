package com.ahmetozaydin.logindemo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmetozaydin.logindemo.databinding.FragmentNfcBinding

class NFCFragment : Fragment() {
        private lateinit var binding : FragmentNfcBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNfcBinding.inflate(layoutInflater)
        return binding.root
    }
}