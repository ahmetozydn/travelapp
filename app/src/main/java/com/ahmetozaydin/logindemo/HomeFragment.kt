package com.ahmetozaydin.logindemo

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.ahmetozaydin.logindemo.databinding.FragmentHomeBinding
import com.ahmetozaydin.logindemo.view.Stops


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.stopButton.setOnClickListener{
            val intent = Intent(activity, Stops::class.java)// Because Fragment is NOT of Context type, you'll need to call the parent Activity:
           activity?.startActivity(intent)

            /*activity?.let{
                val intent = Intent(activity,DeleteActivity::class.java)
                it.startActivity(intent)
            }*/

        }
        binding.busLocations.setOnClickListener{
            val intent = Intent(activity, BusLocation::class.java)
                activity?.startActivity(intent)

            }
        binding.services.setOnClickListener{
            val intent = Intent(activity, ServiceActivity::class.java)
            activity?.startActivity(intent)
        }

        binding.buttonBuses.setOnClickListener {

        }

        }

    }


