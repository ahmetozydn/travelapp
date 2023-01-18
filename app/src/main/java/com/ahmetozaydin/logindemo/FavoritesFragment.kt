package com.ahmetozaydin.logindemo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.logindemo.adapter.FavoritesAdapter
import com.ahmetozaydin.logindemo.databinding.FragmentFavoritesBinding
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.rcycler_view_stops.view.*

private lateinit var layoutManager: RecyclerView.LayoutManager
private lateinit var favoritesAdapter: FavoritesAdapter

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private var databaseList = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        layoutManager = LinearLayoutManager(
            requireActivity(),
            RecyclerView.VERTICAL,
            false
        )//requireActivity(), RecyclerView.VERTICAL, false
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

        context?.let {
            createSwipeGesture(
                it.applicationContext,
                binding.recyclerViewFavorites
            )
        }
    }

    private fun createSwipeGesture(applicationContext: Context, recyclerView: Any) {
        val swipeGestures = object : SwipeGestures(applicationContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        try {
                            val myDatabase = requireActivity().openOrCreateDatabase(
                                "MyDatabase",
                                Context.MODE_PRIVATE,
                                null
                            )
                            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS descriptions_table (column_description VARCHAR NOT NULL UNIQUE)")
                            /*val sharedPreferences = activity?.getSharedPreferences(
                                "com.ahmetozaydin.logindemo",
                                Context.MODE_PRIVATE
                            )!!*/
                            //val isExecuted = sharedPreferences.getBoolean("isExecuted", true)
                            //  val rowName = services.description?.replace("'", "''")TODO()
                            myDatabase.execSQL("INSERT INTO descriptions_table(column_description) VALUES ('${viewHolder.itemView.description.text}')")//TODO
                            //sharedPreferences.edit().putBoolean("isExecuted", false).apply()
                            val cursor =
                                myDatabase.rawQuery(("SELECT * FROM descriptions_table"), null)
                            val examplesIx = cursor.getColumnIndex("column_description")
                            while (cursor.moveToNext()) {
                                //println("Text: " + cursor.getString(examplesIx))
                                if (!databaseList.contains(cursor.getString(examplesIx))) {
                                    databaseList.add(cursor.getString(examplesIx))
                                }
                            }
                            cursor.close()
                            println(databaseList.size)
                            databaseList.forEach {
                                println(it)
                            }
                            //databaseList.add(viewHolder.itemView.description.text.toString())
                            /* bundle.putStringArrayList("databaseList",databaselist)
                             parentFragmentManager.setFragmentResult("data",bundle)
                             favoritesFragment.arguments*/
                            val bundle = Bundle()
                            bundle.putStringArrayList("arrayList", databaseList)
                        } catch (exception: Exception) {
                            exception.printStackTrace()
                        }
                        recycler_view_favorites.adapter?.notifyItemChanged(viewHolder.absoluteAdapterPosition)
                    }
                    /*  ItemTouchHelper.RIGHT->{
                      }*/
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGestures)
        touchHelper.attachToRecyclerView(recycler_view_favorites)

    }
}





