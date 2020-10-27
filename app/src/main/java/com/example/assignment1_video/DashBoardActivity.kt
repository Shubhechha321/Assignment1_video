package com.example.assignment1_video

//import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dash_board.*


class DashBoardActivity : AppCompatActivity(), Communicator{

    lateinit var homeFragment: HomeFragment

    lateinit var userFragment: UserFragment

    lateinit var searchFragment: SearchFragment




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dash_board)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottomnav)

/*
val fragmentA= SearchFragment()
        supportFragmentManager.beginTransaction()*/


        searchFragment = SearchFragment()

        supportFragmentManager

            .beginTransaction()

            .replace(R.id.frame, searchFragment)

            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

            .commit()



        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {



                R.id.search ->{

                    searchFragment = SearchFragment()

                    supportFragmentManager

                        .beginTransaction()

                        .replace(R.id.frame, searchFragment)

                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                        .commit()

                }/*
                R.id.home ->{

                    homeFragment = HomeFragment()

                    supportFragmentManager

                        .beginTransaction()

                        .replace(R.id.frame, homeFragment)

                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

                        .commit()

                }*/
                R.id.home->{
                    homeFragment= HomeFragment()
                    supportFragmentManager

                        .beginTransaction()

                        .replace(R.id.frame, homeFragment)

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

    override fun passData(video_id: String, video_title:String) {
        val bundle = Bundle()
        bundle.putString("video_id", video_id)
        bundle.putString("v_title", title as String?)
        val transaction=this.supportFragmentManager.beginTransaction()
        val fragmentB= HomeFragment()
        fragmentB.arguments=bundle
        transaction.replace(R.id.frame, fragmentB)

        bottomnav.setSelectedItemId(R.id.home)
        transaction.commit()


        //change color of bottom navigation i
       // bottomnav.setSelectedItemId(R.id.home)
    }


}

