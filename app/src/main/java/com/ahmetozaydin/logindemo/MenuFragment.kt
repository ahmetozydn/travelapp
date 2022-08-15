package com.ahmetozaydin.logindemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.ahmetozaydin.logindemo.databinding.FragmentMenuBinding
import com.ahmetozaydin.logindemo.view.MainActivity
import com.google.common.collect.ComparisonChain.start
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MenuFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentMenuBinding






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        /*if(currentUser!=null){
            //TODO check if the user is login and logout, and make logout or login button visible or invisible, you may need to get currentUser from main activity
        binding.logInButton.visibility = View.GONE

        }
*/


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(layoutInflater)// binding must initialized in here



        return binding.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    binding.logOutButton.setOnClickListener{
        val intent = Intent(activity,MainActivity::class.java)
        FirebaseAuth.getInstance().signOut()
        activity?.startActivity(intent)
        activity?.finish()

}
        binding.shareTheAppButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "type/palin"
            val sharebody = "Please download the app"
            val shareSub = "https://play.google.com/store/apps/details?id=com.asis.akillibilet&gl=TR"
            intent.putExtra(Intent.EXTRA_SUBJECT, sharebody)
            intent.putExtra(Intent.EXTRA_TEXT,shareSub)
            startActivity(Intent.createChooser(intent,"Share Our App"))

        }









    }






}