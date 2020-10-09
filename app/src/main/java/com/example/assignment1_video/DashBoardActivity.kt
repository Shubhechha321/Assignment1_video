package com.example.assignment1_video

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.youtube.YouTubeScopes
import pub.devrel.easypermissions.EasyPermissions


class DashBoardActivity : AppCompatActivity(){

    lateinit var homeFragment: HomeFragment

    lateinit var userFragment: UserFragment

    lateinit var searchFragment: SearchFragment




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dash_board)

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

