package com.ahmetozaydin.logindemo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.logindemo.adapter.FavoritesAdapter
import com.ahmetozaydin.logindemo.databinding.FragmentFavoritesBinding
private lateinit var layoutManager : RecyclerView.LayoutManager
private lateinit var favoritesAdapter :FavoritesAdapter
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private var databaseList = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)//requireActivity(), RecyclerView.VERTICAL, false
        binding.recyclerViewFavorites.layoutManager = layoutManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            val myDatabase = requireActivity().openOrCreateDatabase(
                "MyDatabase",
                Context.MODE_PRIVATE,
                null
            )
            val cursor =
                myDatabase.rawQuery(("SELECT * FROM descriptions_table"), null)
            val examplesIx = cursor.getColumnIndex("column_description")
            while (cursor.moveToNext()) {
                databaseList.add(cursor.getString(examplesIx))
            }
            cursor.close()
            println(databaseList.size)
            databaseList.forEach {
                println(it)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
         favoritesAdapter = FavoritesAdapter(databaseList)
        binding.recyclerViewFavorites.adapter = favoritesAdapter



    }

}




