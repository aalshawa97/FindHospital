package com.example.findhospital.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.findhospital.R
import com.example.findhospital.databinding.ActivityMainBinding
import com.example.findhospital.ui.graph.CovidData
import com.example.findhospital.ui.graph.CovidSparkAdapter
import com.example.findhospital.ui.list.Hospital
import com.example.findhospital.ui.list.MyAdapter
import java.lang.NullPointerException
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val BASE_URL = "https://covidtracking.com/api/v1/"
        const val ALL_STATES = "All (Nationwide)"
    }
    private lateinit var adapter: CovidSparkAdapter
    private lateinit var currentlyShownData: List<CovidData>
    private lateinit var perStateDailyData: Map<String, List<CovidData>>
    private lateinit var nationalDailyData: List<CovidData>
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

        try
        {
            binding = ActivityMainBinding.inflate(layoutInflater)
        }
        catch (e : NullPointerException)
        {
            Log.d("MainActivity exception",e.toString())
        }
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
        //this@MainActivity.startActivity(Intent(this@MainActivity, RecyclerActivity::class.java))
        //}

    }
}