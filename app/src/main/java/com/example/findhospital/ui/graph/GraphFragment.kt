package com.example.findhospital.ui.graph
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import com.example.findhospital.R
import com.example.findhospital.databinding.ActivityHomeBinding
import com.example.findhospital.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import android.widget.LinearLayout
import com.example.findhospital.ui.list.HospitalListViewModel
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.findhospital.activity.RecyclerActivity
import com.example.findhospital.databinding.FragmentHospitalListBinding
import java.util.*

class GraphFragment : Fragment() {

    private lateinit var hospitalListViewModel: HospitalListViewModel
    private var _binding: FragmentHospitalListBinding? = null
    //private var intent :Intent =  Intent(activity,  RecyclerActivity::class.java)


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Timer("SettingUp", false).schedule(30000) {
        this@GraphFragment.startActivity(Intent(activity,  RecyclerActivity::class.java))
        //}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hospitalListViewModel =
            ViewModelProvider(this).get(HospitalListViewModel::class.java)

        _binding = FragmentHospitalListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        hospitalListViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
/*
class GraphFragment : Fragment() {
    companion object {
        const val TAG = "MainActivity"
        const val BASE_URL = "https://covidtracking.com/api/v1/"
        const val ALL_STATES = "All (Nationwide)"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.title = getString(R.string.app_description)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(com.example.findhospital.R.layout.activity_graph, container, false)

        // add the data here if necessary
        // ...
        val layout = view.findViewById(com.example.findhospital.R.id.tickerView) as LinearLayout
        layout.addView(view)
        return view
    } // ...
}
*/
/*
class GraphFragment : Fragment(){

    private lateinit var adapter: CovidSparkAdapter
    private lateinit var currentlyShownData: List<CovidData>
    private lateinit var perStateDailyData: Map<String, List<CovidData>>
    private lateinit var nationalDailyData: List<CovidData>

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = getString(R.string.app_description)

        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val covidService = retrofit.create(CovidService::class.java)

        covidService.getNationalData().enqueue(object : Callback<List<CovidData>> {
            override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
                Log.e(TAG, "onFailure $t")
            }

            override fun onResponse(call: Call<List<CovidData>>, response: Response<List<CovidData>>) {
                Log.i(TAG, "onResponse $response")
                val nationalData = response.body()
                if (nationalData == null) {
                    Log.w(TAG, "Did not receive a valid response body")
                    return
                }

                setupEventListeners()
                nationalDailyData = nationalData.reversed()
                Log.i(TAG, "Update graph with national data")
                updateDisplayWithData(nationalDailyData)
            }
        })

        covidService.getStatesData().enqueue(object : Callback<List<CovidData>> {
            override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
                Log.e(TAG, "onFailure $t")
            }

            override fun onResponse(
                call: Call<List<CovidData>>,
                response: Response<List<CovidData>>
            ) {
                Log.i(TAG, "onResponse $response")
                val statesData = response.body()
                if (statesData == null) {
                    Log.w(TAG, "Did not receive a valid response body")
                    return
                }

                perStateDailyData = statesData
                    .filter { it.dateChecked != null }
                    .map { // State data may have negative deltas, which don't make sense to graph
                        CovidData(
                            it.dateChecked,
                            it.positiveIncrease.coerceAtLeast(0),
                            it.negativeIncrease.coerceAtLeast(0),
                            it.deathIncrease.coerceAtLeast(0),
                            it.state
                        ) }
                    .reversed()
                    .groupBy { it.state }
                Log.i(TAG, "Update spinner with state names")
                updateSpinnerWithStateData(perStateDailyData.keys)
            }
        })
    }*/
}
        //supportActionBar?.title = getString(R.string.app_description)

        //val intent = Intent(this,GraphFragment::class.java)
        //startActivity(intent)
        //this@GraphFragment.startActivity(Intent(activity, MainActivityJ::class.java))
        //var binding : ViewDataBinding? = DataBindingUtil.setContentView(this, R.layout.activity_graph)
        /*




                setupEventListeners()
                nationalDailyData = nationalData.reversed()
                Log.i(TAG, "Update graph with national data")
                updateDisplayWithData(nationalDailyData)
            }
        })

        covidService.getStatesData().enqueue(object : Callback<List<CovidData>> {
            override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
                Log.e(TAG, "onFailure $t")
            }

            override fun onResponse(
                call: Call<List<CovidData>>,
                response: Response<List<CovidData>>
            ) {
                Log.i(TAG, "onResponse $response")
                val statesData = response.body()
                if (statesData == null) {
                    Log.w(TAG, "Did not receive a valid response body")
                    return
                }

                perStateDailyData = statesData
                    .filter { it.dateChecked != null }
                    .map { // State data may have negative deltas, which don't make sense to graph
                        CovidData(
                            it.dateChecked,
                            it.positiveIncrease.coerceAtLeast(0),
                            it.negativeIncrease.coerceAtLeast(0),
                            it.deathIncrease.coerceAtLeast(0),
                            it.state
                        ) }
                    .reversed()
                    .groupBy { it.state }
                Log.i(TAG, "Update spinner with state names")
                updateSpinnerWithStateData(perStateDailyData.keys)
            }
        })
         */






class MainActivity : AppCompatActivity() {

    private fun updateSpinnerWithStateData(stateNames: Set<String>) {
        val stateAbbreviationList = stateNames.toMutableList()
        stateAbbreviationList.sort()
        stateAbbreviationList.add(0, ALL_STATES)
        spinnerSelect.attachDataSource(stateAbbreviationList)
        spinnerSelect.setOnSpinnerItemSelectedListener { parent, _, position, _ ->
            val selectedState = parent.getItemAtPosition(position) as String
            val selectedData = perStateDailyData[selectedState] ?: nationalDailyData
            updateDisplayWithData(selectedData)
        }
    }

    private fun setupEventListeners() {
        sparkView.isScrubEnabled = true
        sparkView.setScrubListener { itemData ->
            if (itemData is CovidData) {
                updateInfoForDate(itemData)
            }
        }
        tickerView.setCharacterLists(TickerUtils.provideNumberList())

        // Respond to radio button selected events
        radioGroupTimeSelection.setOnCheckedChangeListener { _, checkedId ->
            adapter.daysAgo = when (checkedId) {
                R.id.radioButtonWeek -> TimeScale.WEEK
                R.id.radioButtonMonth -> TimeScale.MONTH
                else -> TimeScale.MAX
            }
            // Display the last day of the metric
            updateInfoForDate(currentlyShownData.last())
            adapter.notifyDataSetChanged()
        }
        radioGroupMetricSelection.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonNegative -> updateDisplayMetric(Metric.NEGATIVE)
                R.id.radioButtonPositive -> updateDisplayMetric(Metric.POSITIVE)
                R.id.radioButtonDeath -> updateDisplayMetric(Metric.DEATH)
            }
        }
    }

    private fun updateDisplayMetric(metric: Metric) {
        // Update color of the chart
        @ColorRes val colorRes = when (metric) {
            Metric.NEGATIVE -> R.color.colorNegative
            Metric.POSITIVE -> R.color.colorPositive
            Metric.DEATH -> R.color.colorDeath
        }
        @ColorInt val colorInt = ContextCompat.getColor(this, colorRes)
        sparkView.lineColor = colorInt
        tickerView.textColor = colorInt

        // Update metric on the adapter
        adapter.metric = metric
        adapter.notifyDataSetChanged()

        // Reset number/date shown for most recent date
        updateInfoForDate(currentlyShownData.last())
    }

    private fun updateDisplayWithData(dailyData: List<CovidData>) {
        currentlyShownData = dailyData
        // Create a new SparkAdapter with the data
        adapter = CovidSparkAdapter(dailyData)
        sparkView.adapter = adapter
        // Update radio buttons to select positive cases and max time by default
        radioButtonPositive.isChecked = true
        radioButtonMax.isChecked = true
        updateDisplayMetric(Metric.POSITIVE)
    }

    private fun updateInfoForDate(covidData: CovidData) {
        val numCases = when (adapter.metric) {
            Metric.NEGATIVE -> covidData.negativeIncrease
            Metric.POSITIVE -> covidData.positiveIncrease
            Metric.DEATH -> covidData.deathIncrease
        }
        tickerView.text = NumberFormat.getInstance().format(numCases)
        val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        tvDateLabel.text = outputDateFormat.format(covidData.dateChecked)
    }
}

 */