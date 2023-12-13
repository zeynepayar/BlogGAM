package com.example.bloggam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class anasayfa_Activity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anasayfa)

        bottomNavigationView =findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.bottom_search -> {
                    replaceFragment(SearhFragment())
                    true
                }
                R.id.bottom_save -> {
                    replaceFragment(SaveFragment())
                    true
                }
                R.id.bottom_shopping -> {
                    replaceFragment(ShoppingFragment())
                    true
                }
                R.id.bottom_profil -> {
                    replaceFragment(ProfilFragment())
                    true
                }
                else-> false




            }
        }
        replaceFragment(HomeFragment())
    }
    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()

    }
}