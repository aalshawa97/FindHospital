package com.example.findhospital.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.findhospital.activity.MapsActivity
import com.example.findhospital.R
import com.example.findhospital.databinding.ActivityMainBinding
import com.example.findhospital.ui.list.Hospital
import com.example.findhospital.ui.list.MyAdapter
import com.example.findhospital.ui.list.RecyclerActivity
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: MyAdapter
    private val contactsList: ArrayList<Hospital> = ArrayList()
    private lateinit var recycler: RecyclerView
    private lateinit var makeCallButton : Button
    private val newWordActivityRequestCode = 1

    fun loadList(view: View)
    {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Timer("SettingUp", false).schedule(10000) {
        this@MainActivity.startActivity(Intent(this@MainActivity, RecyclerActivity::class.java))
        //}

        Timer("SettingUp", false).schedule(30000) {
        this@MainActivity.startActivity(Intent(this@MainActivity, MapsActivity::class.java))
        }
    }
}