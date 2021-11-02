package com.example.findhospital.ui.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.findhospital.activity.MapsActivity
import com.example.findhospital.databinding.FragmentDashboardBinding
import java.util.*
import android.content.Context
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.findhospital.models.Place
import com.example.findhospital.models.UserMap
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.ArithmeticException
import java.lang.NullPointerException
import java.sql.Connection
import javax.xml.parsers.DocumentBuilder
import com.example.findhospital.R
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.concurrent.schedule

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    //Declare and intitialize variables
//private static final String GOOGLE_API_KEY = "AIzaSSDFSDF8Kv2eP0PM8adf5dSDFysdfas323SD3HA";
//GoogleMap googleMap;
    val PLAY_SERVICES_RESOLUTION_REQUEST = 0
    private lateinit var mMap: GoogleMap
    private var userMap: UserMap = UserMap(" ",  listOf(Place("Providence Medical Group Primary Care - Newberg", "When you choose Providence Medical Group - Newberg, you’re choosing more than a primary care provider or a clinic location. You’re choosing an integrated network of caregivers, specialists and clinical programs dedicated to compassionate, patient-centered health care.\n" +
            "\n" +
            "It’s all part of your medical home: a coordinated approach that brings together an expert team focused on caring for you—body, soul and mind.\n" +
            "\n" +
            "Providence Medical Group accepts most major forms of insurance. Please contact your insurance carrier to confirm coverage at this clinic.\n" +
            "\n" +
            "Whether it’s internal medicine, family medicine or obstetrics, we look forward to serving you in being your health care professionals.", 45.5472 ,122.6417)))
    val EXTRA_USER_MAP = "EXTRA_USER_MAP"

    fun getUrl(latitude: Double, longitude: Double, nearByPlace: String): String {
        val gmmIntentUri = "hospitals"
        val gmmIntentStringExample = "https://www.google.com/maps/search/hospitals/@45.3756934,-122.7698314,11z/data=!3m1!4b1"
        // Search for hospitals nearby
        try
        {
            println(longitude)
            println(latitude)
            println(nearByPlace)
            val gmmIntentUri = Uri.parse("geo:0,0?q=hospitals")
            //Example string for hospitals in Lake Oswego, Oregon
            println(gmmIntentStringExample)
        }
        catch(e : ArithmeticException)
        {

        }
        return "https://maps.googleapis.com/maps/api/streetview?parameters\n" +  gmmIntentUri
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode,
                    com.example.findhospital.activity.PLAY_SERVICES_RESOLUTION_REQUEST
                )
                    .show()
            } else {
                println("This device is not supported.")
                finish()
            }
            return false
        }
        return true
    }

    private fun finish() {
        TODO("Not yet implemented")
    }


    var type = "type";
    //Custom Setter
    enum class SearchResultType{
        HISTORY, SAVED, BASIC
    }
    var resultType: SearchResultType
        get()
        {
            val resultTypeString = "History"
            return enumValueOf(resultTypeString)
        }
        set(value)
        {
            var setterVisibility: String = "abc"
            // the setter is private and has the default implementation
            var setterWithAnnotation: Any? = null
        }

    interface location {
        fun getLocations(): List<LocationSource>
    }

    private fun Context.getService(): Nothing? {
        // Get the status of the location
        return null
    }

    //private var intent :Intent =  Intent(activity,  MapsActivity::class.java)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Timer("SettingUp", false).schedule(30000) {
            this@DashboardFragment.startActivity(Intent(activity,  MapsActivity::class.java))
       // }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.activity_maps, container, false)

        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun GoogleApiAvailability.isGooglePlayServicesAvailable(dashboardFragment: DashboardFragment): Int {

    return 0
}
