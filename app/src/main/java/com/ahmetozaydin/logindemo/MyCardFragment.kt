package com.ahmetozaydin.logindemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.ahmetozaydin.logindemo.adapter.TabLayoutAdapter
import com.ahmetozaydin.logindemo.databinding.FragmentMyCardBinding
import com.ahmetozaydin.logindemo.fragments.CardIdFragment
import com.ahmetozaydin.logindemo.fragments.NFCFragment
import kotlinx.android.synthetic.main.fragment_my_card.*

class MyCardFragment : Fragment() {

    private lateinit var binding:FragmentMyCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_card, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
    }
    private fun setUpTabs() {
        val adapter = TabLayoutAdapter(childFragmentManager)
        adapter.addFragment(CardIdFragment(),"CARD ID")
        adapter.addFragment(NFCFragment(),"NFC")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

      /*  tabLayout.getTabAt(0)?.setIcon(R.drawable.vector_card)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.vector_card)
*/
    }


}