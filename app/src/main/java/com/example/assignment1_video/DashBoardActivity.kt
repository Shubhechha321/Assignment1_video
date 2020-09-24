package com.example.assignment1_video

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dash_board.*

@Suppress("DEPRECATION")
class DashBoardActivity : AppCompatActivity() {

    lateinit var homeFragment: HomeFragment

    lateinit var userFragment: UserFragment

    lateinit var searchFragment: SearchFragment



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottomnav)





        homeFragment = HomeFragment()

        supportFragmentManager

            .beginTransaction()

            .replace(R.id.frame, homeFragment)

            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

            .commit()



        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {

                R.id.home ->{

                    homeFragment = HomeFragment()

                    supportFragmentManager

                        .beginTransaction()

                        .replace(R.id.frame, homeFragment)

                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                        .commit()

                }

                R.id.search ->{

                    searchFragment = SearchFragment()

                    supportFragmentManager

                        .beginTransaction()

                        .replace(R.id.frame, searchFragment)

                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                        .commit()

                }

                R.id.profile ->{

                    userFragment = UserFragment()

                    supportFragmentManager

                        .beginTransaction()

                        .replace(R.id.frame, userFragment)

                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                        .commit()

                }



            }

            true

        }

    }
}

