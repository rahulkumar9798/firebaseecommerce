package com.example.firebaseecommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.firebaseecommerce.BottomNavigation.BottomCartFragment
import com.example.firebaseecommerce.BottomNavigation.FavouriteFragment
import com.example.firebaseecommerce.BottomNavigation.HomeFragment
import com.example.firebaseecommerce.BottomNavigation.ProfileFragment
import com.example.firebaseecommerce.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.root.findViewById(R.id.myToolbar))

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.root.findViewById(R.id.myToolbar),
            R.string.openDrawer,
            R.string.closeDrawer
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        //Fatch Image In Fire Base and Set Profile Image in User Profile Start.....
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val firestore = Firebase.firestore
        firestore
            .collection("User")
            .document(userId)
            .get()
            .addOnSuccessListener {
                val imgpath = it.get("profilePic").toString()
                val name = it.get("name").toString()
                val email = it.get("email").toString()
                Glide.with(this).load(imgpath).into(binding.root.findViewById(R.id.userProImg))
                binding.root.findViewById<TextView>(R.id.userEmail).text = email
                binding.root.findViewById<TextView>(R.id.userName).text = name


            }
        //Fatch Image In Fire Base and Set Profile Image in User Profile Close.....


        binding.root.findViewById<BottomNavigationView>(R.id.bottomNavigationView) .setOnItemSelectedListener { item->
            when (item.itemId) {
                R.id.bottom_home->{
                    loadFragment(HomeFragment())
                }
                R.id.bottom_Profile -> {
                    loadFragment(ProfileFragment())
                }
                R.id.bottom_Favourite -> {
                    loadFragment(FavouriteFragment())
                }else ->{
                    loadFragment(BottomCartFragment())
                }

            }

            true
        }

    }

    fun loadFragment(frag : Fragment){
//        val fm  = supportFragmentManager
//        val ft = fm.beginTransaction()
//       if(isFirst){
//           //home
//           ft.add(R.id.container, frag)
//       }else{
//           ft.replace(R.id.container, frag)
//       }

        supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
    }


}