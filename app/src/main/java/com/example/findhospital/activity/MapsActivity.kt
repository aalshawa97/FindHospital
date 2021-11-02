package com.example.findhospital.activity

//Abdullah Mutaz Alshawa
//07/08/2020
//Hospital Locator to conduct virtual visits at hospitals with a headset.

//This makes the application an activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.findhospital.R
import com.example.findhospital.models.Place
import com.example.findhospital.models.UserMap
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

//Declare and initialize variables
//private static final String GOOGLE_API_KEY = "AIzaSSDFSDF8Kv2eP0PM8adf5dSDFysdfas323SD3HA";
//GoogleMap googleMap;
const val PLAY_SERVICES_RESOLUTION_REQUEST = 0
private var userMap: UserMap = UserMap(" ",  listOf(Place("Providence Medical Group Primary Care - Newberg", "When you choose Providence Medical Group - Newberg, you’re choosing more than a primary care provider or a clinic location. You’re choosing an integrated network of caregivers, specialists and clinical programs dedicated to compassionate, patient-centered health care.\n" +
        "\n" +
        "It’s all part of your medical home: a coordinated approach that brings together an expert team focused on caring for you—body, soul and mind.\n" +
        "\n" +
        "Providence Medical Group accepts most major forms of insurance. Please contact your insurance carrier to confirm coverage at this clinic.\n" +
        "\n" +
        "Whether it’s internal medicine, family medicine or obstetrics, we look forward to serving you in being your health care professionals.", 45.5472 ,122.6417)))
const val EXTRA_USER_MAP = "EXTRA_USER_MAP"

// fun onOptionItemSelected(item: MenuItem) //= when (item.itemId)
//{
// Change the map type based on the user's selection
//R.id.normal_map -> {
//map.mapType = Google.MAP_TYPE_NORMAL
//       true
//   }
//else -> super.onOptionsItemSelected(item)
//else ->
//}

var type = "type"

/*
interface location {
    fun getLocations(): List<LocationSource>
}
*/

/*
private fun Context.getService(): Nothing? {
    // Get the status of the location
    return null
}
*/

/*
fun onMenuOptionClick() = Unit

fun onClick()
{
    try
    {
    }
    catch (e: Exception)
    {
    }
}

fun onCreateOptionsMenu(menu: Menu) {
    onCreateOptionsMenu(menu)
    //ObjectRenderer()
}
*/

fun main()
{
    Bundle()
    println("Hello, World")
}

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    //Google map;

    /*
    interface IObserver {
        fun update()
    }
    */

    private fun getUrl(latitude: Double = 45.4160844, longitude: Double = -122.7229328, nearByPlace: String = "Providence Mercantile"): String {
        val gmmIntentUri = "hospitals"
        val gmmIntentStringExample = "https://www.google.com/maps/search/hospitals/@45.3756934,-122.7698314,11z/data=!3m1!4b1"
        // Search for hospitals nearby
        try
        {
            println(longitude)
            println(latitude)
            println(nearByPlace)
            Uri.parse("geo:0,0?q=hospitals")
            //Example string for hospitals in Lake Oswego, Oregon
            println(gmmIntentStringExample)
        }
        catch(e : ArithmeticException)
        {

        }
        return "https://maps.googleapis.com/maps/api/streetview?parameters\n$gmmIntentUri"
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(this)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                    ?.show()
            } else {
                println("This device is not supported.")
                finish()
            }
            return false
        }
        return true
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        // Retrieve content vie that renders the map.
        super.onCreate(savedInstanceState)
        try{
            userMap =
                (intent?.getSerializableExtra(EXTRA_USER_MAP) as? UserMap)!!//UserMap("Covid research", ("John Hopkins","University") as Place)
        }
        catch (e : NullPointerException)
        {

        }

        "Providence Medical Group Primary Care - Newberg".also { userMap.places[0].title = it }
        userMap.places[0].description = "\"When you choose Providence Medical Group - Newberg, you’re choosing more than a primary care provider or a clinic location. You’re choosing an integrated network of caregivers, specialists and clinical programs dedicated to compassionate, patient-centered health care.\\n\" +\n" +
                "        \"\\n\" +\n" +
                "        \"It’s all part of your medical home: a coordinated approach that brings together an expert team focused on caring for you—body, soul and mind.\\n\" +\n" +
                "        \"\\n\" +\n" +
                "        \"Providence Medical Group accepts most major forms of insurance. Please contact your insurance carrier to confirm coverage at this clinic.\\n\" +\n" +
                "        \"\\n\" +\n" +
                "        \"Whether it’s internal medicine, family medicine or obstetrics, we look forward to serving you in being your health care professionals.\""
        userMap.places[0].latitude = 45.5472
        userMap.places[0].longitude = 122.6417

        /*
        if(getString(R.string.maps_api_key).isEmpty())
        {
            // Inject language or reference
        }
        */
        if(!isGooglePlayServicesAvailable())
        {
            return
        }
        getUrl(45.4160844, -122.7229328, "Providence Mercantile")
        setContentView(R.layout.activity_maps)
        //Return the FragmentManager for interacting with fragments associated with this activity.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.onResume()
        //Returns whether the exit transition and enter transition overlap or not. When true, the enter transition will start as soon as possible. When false, the enter transition will wait until
        mapFragment.allowEnterTransitionOverlap
        mapFragment.activity

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //Automatically locate hospitals around the user's location
    /*
    private fun createLocationRequest() {
        val Place = "Hospitals"
        val Field ="Covid"
        // 1
        val locationRequest = LocationRequest()
        // 2
        val interval = 10000
        val fastestInterval = 5000
        val locationRequestPriority = "PRIORITY_HIGH_ACCURACY"
        val careType = "Express"
    }

    fun onActivityResult(){
        val resultCode = 0
        if(resultCode == RESULT_OK)
        {
        }
    }
*/

    // Function to print the hashMap
    private fun printHashMap(hashMap: HashMap<String, Int>){
        // isEmpty() function to check whether the hashMap is empty or not
        if(hashMap.isEmpty())
        {
            println("hashMap is empty")
        }
        else
        {
            println("hashMap : $hashMap")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // A simple example of HashMap class define with "HashMap of <String, Int>"
        val hashMap : HashMap<String, Int> = HashMap()
        // Add elements to the hashMap
        hashMap["Texas Medical Center"] = 0
        // Printing the empty hashMap
        printHashMap(hashMap)
        mMap = googleMap

        //Log.i("MapsActivity", "User map to render: ${userMap.title}")
        val text = "Hello and welcome to the hospital locator!"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
        // Add a marker for each hospital
        val stJohnsHealth = LatLng(45.4160609, -122.7229228)
        val memorialHospitalOfSweetwaterCounty = LatLng(45.4160568, -122.7229396)
        val memorialHospitalOfConverseCounty = LatLng(45.4159997, -122.7229728)
        val wyomingMedicalCenter = LatLng(45.415987, -122.7229989)
        val rileyHospitalForChildren = LatLng(45.416043, -122.7229752)
        val penRoseHospital = LatLng(45.4160656, -122.7229924)
        val providenceBridgeport = LatLng(45.39, -122.75)
        val dignityHealth = LatLng(44.26, -121.28)
        val evanstonRegionalHospital = LatLng(41.2431244, -115.4730433)
        val providenceMercantile = LatLng(45.42, -122.72)
        val providenceStVincent = LatLng(45.46, -122.792)
        val oregonHealthAndScienceUniversity = LatLng(45.48, -122.81)
        val universityOfWashingtonMedicalCenter = LatLng(46.53, -123.75)
        val providenceWilametteFallsMedicalCenter = LatLng(45.42, -122.72)
        val johnHopkinsHospital = LatLng(39.3299013, -76.6227064)
        val legacyGoodSamaritan = LatLng(45.42, -122.85)
        val pioneerMemorialHospital = LatLng(44.83, -120.76)
        val providenceCanbyMedicalPlaza = LatLng(45.42, -122.72)
        val legacyMeridianParkMedicalCenter = LatLng(45.42, -122.72)
        val providenceMedicalPlazaSherwood = LatLng(45.42, -122.72)
        val providenceNewbergMedicalCenter = LatLng(45.36, -122.97)
        val oregonClinicSouth = LatLng(45.42, -122.72)
        val clackamasPediatricClinic = LatLng(45.44, -122.80)
        val providenceMedicalGroupBeaverton = LatLng(45.42, -122.72)
        val legacyEmanuelMedicalCenter = LatLng(45.42, -122.72)
        val bostonUniversityHospital = LatLng(45.4160844, -122.7229328)
        val providenceMilwaukieHospital = LatLng(45.45, -123.06)
        val providenceHoodRiver = LatLng(45.56, -122.69)
        val shrinersHospitalsForChildrenQuebec = LatLng(45.4716222, -73.6035359)
        val notreDameHospitalCcsmtlHeadOffice = LatLng(45.4650333, -73.667449)
        val peakMedicalNorthwestIncorporated = LatLng(45.42, -122.74)
        val legacyMountHoodMedicalCenter = LatLng(45.42, -122.72)
        val oregonStateHospital = LatLng(45.42, -122.72)
        val legacySilverton = LatLng(45.42, -122.72)
        val samaritanNorthLincolnHospital = LatLng(45.42, -122.72)
        val tualityCommunityHospital = LatLng(45.42, -122.72)
        val kaiserPermanenteWestsideMedicalCenter = LatLng(45.49, -122.92)
        val providenceCentrailiaHospital = LatLng(45.42, -122.72)
        val stJoesphMedicalClinic = LatLng(45.42, -122.72)
        val samaritanAlbanyGeneralHospital = LatLng(44.63, 123.12)
        val asanteThreeRiversMedicalCenter = LatLng(45.42, -122.72)
        val blueMountainHospital = LatLng(44.42, -118.96)
        val goodShepardHealthCareSystem = LatLng(45.42, -122.72)
        val vaPortlandHealthCareSystem = LatLng(45.48, -122.69)
        val oregonClinicCardiology = LatLng(45.42, -122.72)
        val huntingtonHospital = LatLng(44.26, -121.28)
        val legacySalmonCreek = LatLng(45.42, -122.72)
        val stCharlesMedicalCenter = LatLng(45.42, -122.72)
        val goodSamaritanRegionalMedicalCenter = LatLng(45.42, -122.72)
        val providenceMedicalCenterMedford = LatLng(45.4160126, -122.7230276)
        val skyLakesMedicalCenter = LatLng(45.42, -122.72)
        val lakeDistrictMedicalCenter = LatLng(45.42, -122.72)
        val samariatanLebanonCommunityHospital = LatLng(44.55, 122.91)
        val peaceHealthPeaceHarborMedicalCenter = LatLng(45.42, -122.72)
        val sacredHeartMedicalCenterUniversityDistrict = LatLng(45.42, -122.72)
        val grandeRondeHospital = LatLng(45.32, -118.11)
        val providenceSwindellsResourceCenter = LatLng(45.42, -122.73)
        val salemHealthHospital = LatLng(45.22, -123.08)
        val stAlphonsusMedicalCenter = LatLng(45.42, -122.72)
        val harneyDistrictHospital = LatLng(44.59, -123.27)
        val columbiaMemorialHospital = LatLng(45.42, -122.72)
        val southernCoosHospitalAndHealthCenter = LatLng(45.42, -122.72)
        val woodlandParkHospital = LatLng(38.97, -105.08)
        val curryGeneralHospital = LatLng(45.42, -122.72)
        val coquilleValleyHospital = LatLng(45.42, -122.72)
        val mcKenzieWillametteMedicalCenter = LatLng(45.42, -122.72)
        val midColumbiaMedicalCenterEmergencyRoom = LatLng(45.42, -122.72)
        val adventistHealthTillamook = LatLng(45.42, -122.72)
        val westValleyHospital = LatLng(45.42, -122.72)
        val veteransAffairsMedicalCenterOregon = LatLng(45.42, -122.72)
        val cottageGroveCommunityMedicalCenter = LatLng(45.42, -122.72)
        val stAnthonyHospital = LatLng(45.42, -122.72)
        val adventistHealthPortland = LatLng(45.42, -122.72)
        val eastMorelandHospital = LatLng(45.47, -122.64)
        val palestineHospital = LatLng(31.9508871, -96.005480)
        val bayAreaHospital = LatLng(45.42, -122.72)
        val redmondClinic = LatLng(44.26, -121.28)
        val sageViewPsychiatrists = LatLng(44.07, -121.41)
        val centralWashingtonHospital = LatLng(47.21, -125.11)
        val providenceStMaryMedicalCenterWallaWalla = LatLng(46.75, -123.56)
        val columbiaBasinHospital = LatLng(47.040, -125.120)
        val providenceMountCarmelHospital = LatLng(46.86, -125.12)
        val harrisonMedicalCenter = LatLng(46.46, -125.39)
        val sacredHeartChildrensHospital = LatLng(47.63, -120.39)
        val graysHarborCommunityHospital = LatLng(45.95, -127.36)
        val providenceStPeterHospitalOlympia = LatLng(47.05, -127.33)
        val sacredHeartChildrenHospitalSpokane = LatLng(46.43, -123.02)
        val uwMedicalCenterNorthwestSeattleHospital = LatLng(47.02, -123.66)
        val multiCareGoodSamaritanHospital = LatLng(47.02, -122.88)
        val easternStateHospital = LatLng(45.66, -127.99)
        val westernStateHospital = LatLng(47.18, -127.05)
        val snoqualmieValleyHospital = LatLng(47.00, -123.62)
        val multiCareTacomaGeneralHospital = LatLng(47.40, -123.13)
        val navalHospital = LatLng(46.98, -123.99)
        val highlineMedicalCenter = LatLng(46.98, -123.99)
        val skylineHealth = LatLng(45.86, -123.90)
        val masonGeneralHospital = LatLng(46.95, -125.05)
        val daytonGeneralHospital = LatLng(46.92, -123.10)
        val whitmanHospitalAndMedicalClinics = LatLng(46.87, -119.62)
        val triStateMemorialHospitalAndMedicalCampus = LatLng(46.60, -117.75)
        val mayersMemorialHospital = LatLng(43.43, -130.59)
        val stJoesphsMedicalCenter = LatLng(44.26, -121.28)
        val dignityHealthStRoseDominicanHospital = LatLng(44.26, -121.28)
        val southernHillsHospitalAndMedicalCenter = LatLng(36.10, -117.54)
        val northVistaHospital = LatLng(36.20, -117.40)
        val mountGrantGeneralHospital = LatLng(38.0, -126.31)
        val pershingGeneralHospital = LatLng(37.35, -126.32)
        val sunriseHospitalMedicalCenter = LatLng(36.70, -126.32)
        val northEasternNevadaRegionalHospital = LatLng(44.26, -121.28)
        val carsonTahoeRegionalMedicalCenter = LatLng(44.26, -121.28)
        val hendersonHospital = LatLng(44.26, -121.28)
        val boulderCityHospital = LatLng(40.03, -122.60)
        val renownRegionalMedicalCenter = LatLng(44.26, -121.28)
        val mikeOcallaghanMilitaryMedicalCenter = LatLng(36.2457836, -115.1)
        val springValleyHospitalMedicalCenter = LatLng(40.61, -127.75)
        val mesaViewRegionalHospital = LatLng(44.26, -121.28)
        val utahStateHospital = LatLng(41.56, -120.97)
        val shrinersHospitalsForChildrenSaltLakeCity = LatLng(42.35, -121.08)
        val cacheValleyHospital = LatLng(42.5187368, -121.0647758)
        val ldsHospital = LatLng(42.3501746, -121.0939713)
        val timpanogosRegionalHospital = LatLng(42.2008855, -121.0111058)
        val parkCityHospital = LatLng(40.4956895, -111.9383514)
        val davisHospitalAndMedicalCenter = LatLng(40.9975814, -112.1011331)
        val mcKayDeeHospital = LatLng(41.1100, 111.5715)
        val ucHealthGrandViewHospital = LatLng(39.60, -108.51)
        val encompassHealthRehabilitationHospital = LatLng(39.5952669, -108.5093332)
        val stFrancisMedicalCenter = LatLng(39.5521225, -108.5094666)
        val theWomensHospitalOfTexas = LatLng(32.9620665, -103.7423977)
        val texasHealthMedicalHearthMethodistHospital = LatLng(34.1545167, -101.5626582)
        val texasMedicalCenter = LatLng(39.3299013, -76.6227064)
        val texasOrthopedicHospital = LatLng(32.9620665, -103.7423977)
        val iranGeneralHospital = LatLng(32.9620665, -103.7423977)
        val baylorScottAndWhiteMedicalCenter = LatLng(30.4422841, -109.9256475)
        val reevesCountyHospitalDistrict = LatLng(33.105864, -107.6982278)
        val texasHealthHarrisMethodistHospital = LatLng(32.8196653, -103.7431051)
        val texasHealthPresbyterianHospitalDenton = LatLng(33.7563847, -103.4794332)
        val universityOfUtahHospital = LatLng(43.2158724, -121.7791681)
        val medicalCenterOfOdessa = LatLng(31.8438456, -104.8752377)
        val theWomensHospitalOfTexasPediatrics = LatLng(28.3151712, -103.0696408)
        val headacheAndMigraineTreatmentCenter = LatLng(45.4168815, -122.7236538)
        val herberValleyHospital = LatLng(45.4160704, -122.7229424)
        val shrinersSaltLakeCity = LatLng(45.416029, -122.7229606)
        val brighamCityCommunityHospital = LatLng(45.4160255, -122.7230086)
        val utSouthwesternMedicalSchool = LatLng(32.8159489, -96.8477666)
        val childrenMedicalCenterDallas = LatLng(32.8087663, -96.8412482)
        val baylorScottMedical = LatLng(45.4160431, -122.7230584)
        val firstTexasHospitalCyFair = LatLng(36.5292898, -127.1325503)
        val memorialHermannTexasMedicalCenter = LatLng(29.7048122, -95.4173518)
        val scottishRiteForChildren = LatLng(32.8021236, -96.8163139)
        val hcaHoustonMainland = LatLng(45.4474965, -122.7386516)
        val bostonUniversityMedicalCenter = LatLng(42.3474618, -71.1208845)
        val setonMedicalCenterHarkerHeights = LatLng(32.2215139, -98.8221159)
        val memorialHospitalOfTexasCounty = LatLng(32.8011548, -103.7446915)
        val nellJRedfieldMemorialHospital = LatLng(43.393482, -121.8445067)
        val benewahCommunityHospital = LatLng(46.2976093, -124.1433382)
        val weiserMemorialHospital = LatLng(45.4160684, -122.7229575)
        val minidokaMemorialHospital = LatLng(43.8637729, -123.0177321)
        val christusSantaRosaHospitalWestoverHills = LatLng(28.5893737, -101.1299454)
        val texasChildrensHospital = LatLng(28.5893737, -101.1299454)
        val surgicalHospitalOfOklahoma = LatLng(39.2089561, -128.0783334)
        val mercyHospitalOklahomaCity = LatLng(35.493318, -97.6409485)
        val curaHealth = LatLng(35.4931561, -97.6409498)
        val communityHospitalNorth = LatLng(35.4931561, -97.6409498)
        val oklahomaHeartHospitalHeart = LatLng(38.979045, -128.6869605)
        val westTennesseeHealthcareVolunteer = LatLng(35.6115483, -91.4767948)
        val universityOfTennesseeMedicalCenter = LatLng(35.6115483, -91.2752639)
        val leBonheurChildrensHospital = LatLng(35.6115483, -91.4764742)
        val vanderbiltWilsonCountyHospital = LatLng(35.6115483, -91.4764742)
        val jacksonMadisonCountyGeneralHospital = LatLng(45.4160573, -122.7229612)
        val cumberlandMedicalHospital =  LatLng(45.4160573, -122.7229612)
        val westTennesseeHospital = LatLng(37.8891789, -123.7167999)
        val sentaraVirginiaBeachGeneralHospital = LatLng(45.4159953, -122.7229961)
        val senataraPrincessAnneHospital = LatLng(45.4160653, -122.7229121)
        val virginiaHospitalCenter = LatLng(45.4160653, -122.7229121)
        val naturalMedicineUniversity = LatLng(45.4934574, -122.807967)
        val bonSecoursRichmondCommunityHospital = LatLng(35.8205637, -135.810595)
        val ascensionStThomasHospital = LatLng(45.41557, -122.7313178)
        val eastTennesseeHospital = LatLng(45.416067, -122.7229058)
        val nashvilleGeneralHospital = LatLng(45.416067, -122.7229058)
        val iuHealthUniversity = LatLng(45.416043, -122.7229752)
        val kingsCollegeHospital = LatLng(45.4160732, -122.7229641)
        val canadianSpecialistHospital = LatLng(45.4160732, -122.7229641)
        val medeor247HospitalDubaiConsulatesArea = LatLng(45.4512965, -122.7818506)
        val freseniusMedicalCareAtLegacyGoodSamaritanHospital = LatLng(45.416055, -122.7229539)
        val kindredHospitalIndianapolis = LatLng(42.2614601, -122.4086393)
        val mbfJbrHospital = LatLng(45.4160732, -122.7229641)
        val thumbayHospitalDubai = LatLng(45.4160732, -122.7223333339641)
        val alZahraHospital = LatLng(45.4160732, -122.7229641)
        val nmcRoyalHospital = LatLng(45.4160732, -122.7229641)
        val drSulaimanAlHabibHospitalInDubai = LatLng(45.4160732, -122.7229641)
        val mediclinicCityHospitalNorthWing = LatLng(45.4160732, -122.7229641)
        val emiratesHospitalJumeriahBeach = LatLng(45.4160732, -122.7229641)
        val gargashHospitalInDubai = LatLng(45.4160732, -122.7229641)
        val peakMedicalNorthwest = LatLng(45.4166679, -122.7268154)
        val providenceExpressCareKruseWay = LatLng(45.4171074, -122.7262975)
        val newHampshireHospital = LatLng(43.821318, -72.6407463)
        val newLondonHospital = LatLng(43.8103583, -72.640777)
        val brighamAndWomensHospital = LatLng(45.5441086, -122.4198746)
        val newEnglandBaptistHospital = LatLng(45.4160577, -122.7229065)
        val floatingHospitalForChildrenHospital = LatLng(45.5054205, -122.4375266)
        val jebelAliHospital = LatLng(45.4160461, -122.7229256)
        val massachusettsGeneralHospital = LatLng(45.4160544, -122.7229401)
        val shrinersHospitalForChildrenBoston = LatLng(36.884958, -132.6318486)
        val massachusettsInstituteOfTechnologyMedicalCenterPediatrics = LatLng(42.3361037, -71.1588672)
        mMap.addMarker(
            MarkerOptions().position(evanstonRegionalHospital).title("Evanston Regional Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(palestineHospital).title("Palestine Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(jebelAliHospital).title("Jebel Ali Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(wyomingMedicalCenter).title("Wyoming Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(stJohnsHealth).title("St. John's Health")
        )
        mMap.addMarker(
            MarkerOptions().position(memorialHospitalOfSweetwaterCounty).title("Memorial Hospital of Sweetwater County")
        )
        mMap.addMarker(
            MarkerOptions().position(memorialHospitalOfConverseCounty).title("Memorial Hospital of Converse County")
        )
        mMap.addMarker(
            MarkerOptions().position(massachusettsGeneralHospital).title("Massachusetts General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(massachusettsInstituteOfTechnologyMedicalCenterPediatrics).title("Massachusetts Institute of Technology Medical Center Pediatrics")
        )
        mMap.addMarker(
            MarkerOptions().position(floatingHospitalForChildrenHospital).title("Floating Hospital For Children Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(bostonUniversityMedicalCenter).title("Boston University Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(bostonUniversityHospital).title("Boston University Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(brighamAndWomensHospital).title("Brigham And Women's Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(newEnglandBaptistHospital).title("Shriners Hospital For Children Boston")
        )
        mMap.addMarker(
            MarkerOptions().position(shrinersHospitalForChildrenBoston).title("Shriners Hospital For Children Boston")
        )
        mMap.addMarker(
            MarkerOptions().position(newLondonHospital).title("New London Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(newHampshireHospital).title("New Hampshire Hospital")
        )
        mMap.addMarker(

            MarkerOptions().position(peakMedicalNorthwest).title("Peak Medical Northwest")
        )
        mMap.addMarker(

            MarkerOptions().position(providenceExpressCareKruseWay).title("Providence Express Care Kruse Way")
        )
        mMap.addMarker(

            MarkerOptions().position(gargashHospitalInDubai).title("Gargash Hospital In Dubai")
        )
        mMap.addMarker(

            MarkerOptions().position(drSulaimanAlHabibHospitalInDubai).title("Dr. Sulaiman Al Habib Hospital In Dubai")
        )
        mMap.addMarker(
            MarkerOptions().position(nmcRoyalHospital).title("NMC Royal Hospital City Hospital North Wing")
        )
        mMap.addMarker(
            MarkerOptions().position(mediclinicCityHospitalNorthWing).title("Mediclinic City Hospital North Wing")
        )

        mMap.addMarker(
            MarkerOptions().position(alZahraHospital).title("Al Zahra Hospital")
        )

        mMap.addMarker(
            MarkerOptions().position(thumbayHospitalDubai).title("Thumbay Hospital Dubai")
        )

        mMap.addMarker(

            MarkerOptions().position(bonSecoursRichmondCommunityHospital).title("Bon Secours Richmond Community Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(encompassHealthRehabilitationHospital).title("Encompass Health Rehabilitation Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(emiratesHospitalJumeriahBeach).title("Emirates Hospital Jumeriah Beach")
        )
        mMap.addMarker(
            MarkerOptions().position(mbfJbrHospital).title("MBF JBR Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(canadianSpecialistHospital).title("Canadian Specialist Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(medeor247HospitalDubaiConsulatesArea).title("Fresenius Medical Care At Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(freseniusMedicalCareAtLegacyGoodSamaritanHospital).title("Fresenius Medical Care At Legacy Good Samaritan Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(kingsCollegeHospital).title("Kings College Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(kindredHospitalIndianapolis).title("Kindred Hospital Indianapolis")
        )
        mMap.addMarker(
            MarkerOptions().position(nashvilleGeneralHospital).title("Nashville General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(rileyHospitalForChildren).title("rileyHospitalForChildren")
        )
        mMap.addMarker(
            MarkerOptions().position(iuHealthUniversity).title("IU Health Hospital Hospital")
        )
        mMap.addMarker(

            MarkerOptions().position(sentaraVirginiaBeachGeneralHospital).title("Sentara Virginia Beach General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(westTennesseeHealthcareVolunteer).title("West Tennessee Healthcare Volunteer")
        )
        mMap.addMarker(
            MarkerOptions().position(eastTennesseeHospital).title("East Tennessee Hospital")
        )
        mMap.addMarker(

            MarkerOptions().position(ascensionStThomasHospital).title("Ascension St. Thomas Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(naturalMedicineUniversity).title("Natural Medicine University")
        )
        mMap.addMarker(
            MarkerOptions().position(virginiaHospitalCenter).title("Virginia Hospital Center")
        )
        mMap.addMarker(
            MarkerOptions().position(westTennesseeHospital).title("West Tennessee Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(cumberlandMedicalHospital).title("Cumberland Medical Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(jacksonMadisonCountyGeneralHospital).title("Jackson Madison County General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(leBonheurChildrensHospital).title("Le Bonheur Children's Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(vanderbiltWilsonCountyHospital).title("Vanderbilt Wilson County Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(universityOfTennesseeMedicalCenter).title("West Tennessee Healthcare Volunteer")
        )
        mMap.addMarker(
            MarkerOptions().position(senataraPrincessAnneHospital).title("sentara Princess Anne Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(oklahomaHeartHospitalHeart).title("Oklahoma Heart Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(curaHealth).title("Curahealth")
        )
        mMap.addMarker(
            MarkerOptions().position(communityHospitalNorth).title("Community Hospital North")
        )
        mMap.addMarker(
            MarkerOptions().position(surgicalHospitalOfOklahoma).title("Surgical Hospital Of Oklahoma")
        )
        mMap.addMarker(
            MarkerOptions().position(mercyHospitalOklahomaCity).title("Mercy Hospital of Oklahoma City")
        )
        mMap.addMarker(
            MarkerOptions().position(christusSantaRosaHospitalWestoverHills).title("Christus Santa Rosa Hospital Westover Hills")
        )
        mMap.addMarker(
            MarkerOptions().position(texasChildrensHospital).title("Texas Children's Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(benewahCommunityHospital).title("Benewah Community Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(minidokaMemorialHospital).title("Minidoka Memorial Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(weiserMemorialHospital).title("Weiser Memorial Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(nellJRedfieldMemorialHospital).title("Nell J. Redfield Memorial Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(memorialHospitalOfTexasCounty).title("Seton Medical Center Harker Heights")
        )
        mMap.addMarker(
            MarkerOptions().position(hcaHoustonMainland).title("HCA Houston Mainland")
        )
        mMap.addMarker(
            MarkerOptions().position(setonMedicalCenterHarkerHeights).title("Seton Medical Center Harker Heights")
        )
        mMap.addMarker(
            MarkerOptions().position(memorialHermannTexasMedicalCenter).title("Memorial Hermann Texas Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(scottishRiteForChildren).title("Scottish Rite For Children")
        )
        mMap.addMarker(
            MarkerOptions().position(firstTexasHospitalCyFair).title("First Texas Hospital CyFair")
        )
        mMap.addMarker(
            MarkerOptions().position(baylorScottMedical).title("Baylor Scott")
        )
        mMap.addMarker(
            MarkerOptions().position(utSouthwesternMedicalSchool).title("utSouthwesternMedicalSchool")
        )
        mMap.addMarker(
            MarkerOptions().position(childrenMedicalCenterDallas).title("Children's Medical Center Dallas")
        )
        mMap.addMarker(
            MarkerOptions().position(shrinersSaltLakeCity).title("Shriners Salt Lake City")
        )
        mMap.addMarker(
            MarkerOptions().position(herberValleyHospital).title("Herber Valley Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(headacheAndMigraineTreatmentCenter).title("Headache and Migraine Treatment Center")
        )
        mMap.addMarker(
            MarkerOptions().position(theWomensHospitalOfTexasPediatrics).title("Memorial Hospital Of Texas County")
        )
        mMap.addMarker(
            MarkerOptions().position(memorialHospitalOfTexasCounty).title("Memorial Hospital Of Texas County")
        )
        mMap.addMarker(
            MarkerOptions().position(medicalCenterOfOdessa).title("Medical Center of Odessa")
        )
        mMap.addMarker(
            MarkerOptions().position(universityOfUtahHospital).title("University Of Utah Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(texasHealthPresbyterianHospitalDenton).title("Texas Health Presbyterian Hospital Denton")
        )
        mMap.addMarker(
            MarkerOptions().position(texasHealthHarrisMethodistHospital).title("Texas Health Harris Methodist Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(texasHealthMedicalHearthMethodistHospital).title("Texas Health Medical Hearth Methodist Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(baylorScottAndWhiteMedicalCenter).title("Baylor Scott and White Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(reevesCountyHospitalDistrict).title("Reeves County Hospital District")
        )
        mMap.addMarker(
            MarkerOptions().position(texasMedicalCenter).title("Texas Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(iranGeneralHospital).title("Iran General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(texasOrthopedicHospital).title("Texas Orthopedic Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(johnHopkinsHospital).title("John Hopkins University")
        )
        mMap.addMarker(
            MarkerOptions().position(texasHealthMedicalHearthMethodistHospital).title("Texas Health Medical Hearth Methodist Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(theWomensHospitalOfTexas).title("The women's hospital of Texas")
        )
        mMap.addMarker(
            MarkerOptions().position(penRoseHospital).title("Penrose Hospitals")
        )
        mMap.addMarker(
            MarkerOptions().position(stFrancisMedicalCenter).title("Encompass Health Rehabilitation Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(ucHealthGrandViewHospital).title("UC Health Grand View Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(mcKayDeeHospital).title("McKay Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(davisHospitalAndMedicalCenter).title("Davis Hospital And Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(parkCityHospital).title("Park City Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(timpanogosRegionalHospital).title("Timpanogos Regional Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(ldsHospital).title("Cache Valley Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(cacheValleyHospital).title("Cache Valley Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(notreDameHospitalCcsmtlHeadOffice).title("Notre Dame Hospital CCSMTL Head Office")
        )
        mMap.addMarker(
            MarkerOptions().position(shrinersHospitalsForChildrenSaltLakeCity).title("Shriners Hospitals For Children Salt Lake City")
        )
        mMap.addMarker(
            MarkerOptions().position(utahStateHospital).title("Utah State Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(mesaViewRegionalHospital).title("Mesa View Regional Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(springValleyHospitalMedicalCenter).title("Mike O'Callaghan Military Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(boulderCityHospital).title("Boulder City Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(mikeOcallaghanMilitaryMedicalCenter).title("Mike O'Callaghan Military Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(renownRegionalMedicalCenter).title("Renown Regional Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(hendersonHospital).title("Henderson Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(carsonTahoeRegionalMedicalCenter).title("Carson Tahoe Regional Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(northEasternNevadaRegionalHospital).title("North Eastern Nevada Regional Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(sunriseHospitalMedicalCenter).title("Sunrise Hospital Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(pershingGeneralHospital).title("Pershing General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(mountGrantGeneralHospital).title("Mount Grant General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(northVistaHospital).title("North Vista Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(southernHillsHospitalAndMedicalCenter).title("Southern Hills Hospital and Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(dignityHealthStRoseDominicanHospital).title("Dignity Health")
        )
        mMap.addMarker(
            MarkerOptions().position(stJoesphsMedicalCenter).title("St. Joesph's Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(huntingtonHospital).title("Huntington Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(dignityHealth).title("Dignity Health")
        )
        mMap.addMarker(
            MarkerOptions().position(mayersMemorialHospital).title("Mayers Memorial Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(whitmanHospitalAndMedicalClinics).title("Whitman Hospital and Medical Clinics")
        )
        mMap.addMarker(
            MarkerOptions().position(triStateMemorialHospitalAndMedicalCampus).title("Tristate Memorial Hospital and Medical Campus")
        )
        mMap.addMarker(
            MarkerOptions().position(daytonGeneralHospital).title("Dayton General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(masonGeneralHospital).title("Mason General Health")
        )
        mMap.addMarker(
            MarkerOptions().position(skylineHealth).title("Skyline Health")
        )
        mMap.addMarker(
            MarkerOptions().position(highlineMedicalCenter).title("Highline Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(navalHospital).title("Naval Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(multiCareTacomaGeneralHospital).title("MultiCare Tacoma General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(snoqualmieValleyHospital).title("Snoqualmie Valley Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(westernStateHospital).title("Western State Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(easternStateHospital).title("Eastern State Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(multiCareGoodSamaritanHospital).title("MultiCare Good Samaritan")
        )
        mMap.addMarker(
            MarkerOptions().position(uwMedicalCenterNorthwestSeattleHospital).title("UW Medical Center Northwest Seattle Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(sacredHeartChildrenHospitalSpokane).title("Sacred Heart Hospital Spokane")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceStPeterHospitalOlympia).title("Providence St. Peter Hospital Olympia")
        )
        mMap.addMarker(
            MarkerOptions().position(graysHarborCommunityHospital).title("Gray's Harbor Community")
        )
        mMap.addMarker(
            MarkerOptions().position(sacredHeartChildrensHospital).title("Sacred Heart Children's Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(harrisonMedicalCenter).title("Harrison Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceMountCarmelHospital).title("Providence Mount Carmel Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(columbiaBasinHospital).title("Columbia Basin Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceStMaryMedicalCenterWallaWalla).title("Providence St.Mary Medical Center Walla Walla")
        )
        mMap.addMarker(
            MarkerOptions().position(centralWashingtonHospital).title("Central Washington Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(pioneerMemorialHospital).title("Pioneer Memorial Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(sageViewPsychiatrists).title("Sage View Psychiatrists")
        )
        mMap.addMarker(
            MarkerOptions().position(redmondClinic).title("Redmond Clinic")
        )
        mMap.addMarker(
            MarkerOptions().position(bayAreaHospital).title("Bay Area Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(eastMorelandHospital).title("East Moreland Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(legacyMountHoodMedicalCenter).title("Legacy Mount Hood")
        )
        mMap.addMarker(
            MarkerOptions().position(adventistHealthPortland).title("Adventist Health Portland")
        )
        mMap.addMarker(
            MarkerOptions().position(stAnthonyHospital).title("St. Anthony Hospital ")
        )
        mMap.addMarker(
            MarkerOptions().position(cottageGroveCommunityMedicalCenter).title("Cottage Grove Community Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(veteransAffairsMedicalCenterOregon).title("Veterans Affairs Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(westValleyHospital).title("West Valley Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(adventistHealthTillamook).title("Adventist Health Center")
        )
        mMap.addMarker(
            MarkerOptions().position(midColumbiaMedicalCenterEmergencyRoom).title("Mid Columbia Medical Center Emergency Room")
        )
        mMap.addMarker(
            MarkerOptions().position(mcKenzieWillametteMedicalCenter).title("McKenzie Willamette Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(coquilleValleyHospital).title("Coquille Valley Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(curryGeneralHospital).title("Curry General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(woodlandParkHospital).title("Woodland Park Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(legacyEmanuelMedicalCenter).title("Legacy Emanuel Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(southernCoosHospitalAndHealthCenter).title("Southern Coos Hospital and Health Center")
        )
        mMap.addMarker(
            MarkerOptions().position(columbiaMemorialHospital).title("Columbia Memorial Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(harneyDistrictHospital).title("Harney District Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(stAlphonsusMedicalCenter).title("St. Alphonsus Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(salemHealthHospital).title("Salem Health Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceSwindellsResourceCenter).title("Providence Swindells Resource Center")
        )
        mMap.addMarker(
            MarkerOptions().position(grandeRondeHospital).title("Grande Ronde Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(sacredHeartMedicalCenterUniversityDistrict).title("Sacred Heart Medical Center University District")
        )
        mMap.addMarker(
            MarkerOptions().position(peaceHealthPeaceHarborMedicalCenter).title("Peace Health Peace Harbor Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(samariatanLebanonCommunityHospital).title("Samariatan Lebanon Community Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(lakeDistrictMedicalCenter).title("Lake District Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(skyLakesMedicalCenter).title("Sky Lakes medical center Klamath Falls")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceMedicalCenterMedford).title("Providence Medical Center Medford ")
        )
        mMap.addMarker(
            MarkerOptions().position(goodSamaritanRegionalMedicalCenter).title("St. Charles Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(stCharlesMedicalCenter).title("St. Charles Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(legacySalmonCreek).title("Legacy Salmon Creek")
        )
        mMap.addMarker(
            MarkerOptions().position(oregonClinicCardiology).title("Oregon Clinic Cardiology")
        )
        mMap.addMarker(
            MarkerOptions().position(vaPortlandHealthCareSystem).title("Veteran Affairs Portland Health Care System")
        )
        mMap.addMarker(
            MarkerOptions().position(oregonHealthAndScienceUniversity).title("Oregon Health and Science University")
        )
        mMap.addMarker(
            MarkerOptions().position(goodShepardHealthCareSystem).title("Good Shepard Health Care System")
        )
        mMap.addMarker(
            MarkerOptions().position(blueMountainHospital).title("Blue Mountain Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(asanteThreeRiversMedicalCenter).title("Asante Three Rivers Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(samaritanAlbanyGeneralHospital).title("Samaritan Albany General Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(stJoesphMedicalClinic).title("St. Joesph Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceCentrailiaHospital).title("Providence Centrailia Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(kaiserPermanenteWestsideMedicalCenter).title("Kaiser Permanente Westside Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(tualityCommunityHospital).title("Tuality Community Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(samaritanNorthLincolnHospital).title("Samaritans North Lincoln Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(legacySilverton).title("Legacy Silverton Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(oregonStateHospital).title("Oregon State Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(peakMedicalNorthwestIncorporated).title("Peak Medical Northwest Incorporated")
        )
        mMap.addMarker(
            MarkerOptions().position(shrinersHospitalsForChildrenQuebec).title("Shriners Hospital for Children, Quebec")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceHoodRiver).title("Providence Hood River Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceMilwaukieHospital).title("Providence Milwaukie Hospital")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceMedicalGroupBeaverton).title("Providence Medical Group Beaverton")
        )
        mMap.addMarker(
            MarkerOptions().position(clackamasPediatricClinic).title("Clackamas Pediatric Clinic")
        )
        mMap.addMarker(
            MarkerOptions().position(oregonClinicSouth).title("Oregon Clinic South")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceNewbergMedicalCenter).title("Providence Newberg Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceMedicalPlazaSherwood).title("Providence Medical Plaza Sherwood")
        )
        mMap.addMarker(
            MarkerOptions().position(legacyMeridianParkMedicalCenter).title("Legacy Meridian Park Medical Plaza")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceCanbyMedicalPlaza).title("Providence Medical Plaza")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceWilametteFallsMedicalCenter).title("Providence Wilamette Falls Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(legacyGoodSamaritan).title("Legacy Good Samaritan")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceBridgeport).title("Providence Bridgeport")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceMercantile).title("Providence Mercantile")
        )
        mMap.addMarker(
            MarkerOptions().position(providenceStVincent).title("Providence St. Vincent")
        )
        mMap.addMarker(
            MarkerOptions().position(universityOfWashingtonMedicalCenter).title("University of Washington Medical Center")
        )
        mMap.addMarker(
            MarkerOptions().position(brighamCityCommunityHospital).title("Brigham City Community Hospital")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(providenceBridgeport))

        val naturalMedicine:HashMap<String, Int> = HashMap()
        naturalMedicine["honey"] = 1

        for (place in userMap.places){
            val latLng = LatLng(place.latitude, place.longitude)
            mMap.addMarker(MarkerOptions().position(latLng).title(place.title).snippet(place.description))
        }

    }

    //private lateinit var constraintLayout: ConstraintLayout

}

/*
fun loadPlacePicker()
{
    try{

    }
    catch (e: GooglePlayServicesRepairableException)
    {
        e.printStackTrace()
    }
}
*/