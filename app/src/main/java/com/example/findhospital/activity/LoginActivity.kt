package com.example.findhospital.activity

//import com.google.android.gms.common.api.GoogleSignInClient
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findhospital.models.UserMap
import com.example.findhospital.R
import com.example.findhospital.models.Place
import com.example.findhospital.ui.map.MapsAdapter
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.android.synthetic.main.activity_login.*
//import kotlinx.android.synthetic.main.activity_show_hospitals.*
import java.util.*
import kotlin.concurrent.schedule

class LoginActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null
    private var googleApiClient: GoogleApiClient? = null
    private var rootRef: FirebaseFirestore? = null
    private lateinit var previewImage: ImageView
    private var imageUri: Uri? = null

    // One Button
    var bSelectImage: Button? = null
    var bSkip: Button? = null
    var bChat: Button? = null
    var toolBar: Toolbar? = null
    val pickImage = 100
    //Constant to compare the activity result code
    var selectPicture = 200

    private fun generateSampleData(): List<UserMap> {
        return listOf(
            UserMap(
                "Drive-thru covid testing",
                listOf(
                    Place("COVID-19 Drive-Thru Testing at Walgreens", "COVID-19 testing center\n" +
                            "Appointment required\n" +
                            "Referral not required\n" +
                            "Tests limited to certain patients\n" +
                            "Drive-through\n" +
                            "Instructions: 1.) Complete a quick questionnaire. 2.) Choose a location and time for your COVID-19 test (based on the type of test you want). 3.) Go to the testing location and remain in your vehicle with the window rolled up. 4.) Show your confirmation email, a valid state ID or driver's license, and insurance card or voucher. 5.) Perform the nasal swab yourself under the direction of a pharmacy team member. Patients ages 3-18 will need a parent or legal guardian while they self-administer the COVID-19 test. 6.) Get results from PWNHealth", 45.5472, -122.6417),
                    Place("Walmart Drive Thru Testing", "COVID-19 testing center\n" +
                            "Appointment required\n" +
                            "Referral not required\n" +
                            "Testing for all patients\n" +
                            "Drive-through\n" +
                            "Verify testing center info before going.", 45.5472, -122.6417),
                    Place("GS Labs Testing - Lake Oswego\n", "COVID-19 testing center\n" +
                            "Appointment required\n" +
                            "Referral not required\n" +
                            "Testing for all patients\n" +
                            "Drive-through\n" +
                            "Verify testing center info before going.", 45.4738589, -122.7599947)
                )
            ),
            UserMap("Covid research centers",
                listOf(
                    Place("Fred Hutch", "COVID-19 Clinical Research Center", 45.5472, -122.6417),
                    Place("NIAID", "NIAID Office of Communications and Government Relations\n" +
                            "5601 Fishers Lane, MSC 9806\n" +
                            "Bethesda, MD 20892-9806 (deliveries: Rockville, MD 20852)\n" +
                            "United States of America\n" +
                            "\n", 39.0633222, -77.1208334),
                    Place("Fred Hutch", "COVID-19 Clinical Research Center", 47.6266759, -122.3329178)
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        val userMaps = generateSampleData()
        val rvHospital = findViewById<RecyclerView>(R.id.rvHospitals)

        //Toolbar
        //toolBar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolBar)
        //Set layout manager on the recycler view

        if(rvHospital != null)
        {
            rvHospital.layoutManager = LinearLayoutManager(this)
            //Set adapter on recycler view
            rvHospital.adapter = MapsAdapter(this, userMaps, object: MapsAdapter.OnClickListener{
                fun onItemClick(position: Int){
                    Log.i("Login Activity", "onItemClick $position")
                    //When user taps on view in recycler view, navigate to new activity
                    val intent = Intent(this@LoginActivity, MapsActivity::class.java)
                    startActivity(intent)
                }
            })
        }

        //When user taps on a new activity we need to go to it!
        //layoutManager
        val options = FirebaseOptions.Builder()
            .setApplicationId("1:123140511070:android:a5c84837bb6de83502ebcf") // Required for Analytics.
            .setProjectId("hospitallocator-be5cd") // Required for Firebase Installations.
            .setApiKey("AIzaSyDhp9velxKCu4zR7j8iQJTuJvGY9pjnPoU") // Required for Auth.
            .build()
        //If the application name is not initialized!
        /*
        if(R.string.app_name == null)
        {
            FirebaseApp.initializeApp(this, options, "HospitalLocator")
        }
        */

        setContentView(R.layout.activity_login)
        bSelectImage = findViewById(R.id.BSelectImage)
        previewImage = findViewById(R.id.imageView)
        bSelectImage?.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)

            // create an instance of the
            // intent of the type image
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT

            //Pass the constant to compare it with the returned requestCode
            startActivityForResult(Intent.createChooser(i, "Select Picture"), selectPicture)
        }
        rootRef = FirebaseFirestore.getInstance()
        //google_sign_in_button.setOnClickListener { signIn() }

        firebaseAuth = FirebaseAuth.getInstance()
        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val firebaseUser = auth.currentUser
            if(auth.currentUser != null)
            {
                Toast.makeText(this, auth.currentUser?.displayName + " is signed on!", Toast.LENGTH_LONG).show()

            }

            //val idToken: GoogleIdToken = verifier.verify(idTokenString)

            if (firebaseUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()

        googleApiClient = GoogleApiClient.Builder(applicationContext)
            .enableAutoManage(this) { makeText(this, "You got a GoogleApiClient Error!", LENGTH_SHORT).show() }
            .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
            .build()

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        /*
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        */

        //Skip login
        bSkip = findViewById(R.id.buttonSkipLogin)
        bSkip?.setOnClickListener {
            val myIntent = Intent(this@LoginActivity, HomeActivity::class.java)
            this@LoginActivity.startActivity(myIntent)
        }

        //Open chat
        bChat = findViewById(R.id.buttonChat)
        bChat?.setOnClickListener {
            Log.d("LoginActivity", "openChat: ")
            var myIntent = Intent(this@LoginActivity, RSAactivity::class.java)
            //this@LoginActivity.startActivity(myIntent)
            myIntent = Intent(this@LoginActivity, MainActivityJ::class.java)
            this@LoginActivity.startActivity(myIntent)
            Log.d("Opening messenger", "openChat: ")
            Timer("SettingUp", false).schedule(20000) {
                Intent(this@LoginActivity, ChatActivity::class.java)
                //this@LoginActivity.startActivity(myIntent)
            }
            this@LoginActivity.startActivity(myIntent)
        }

        //Send message

        /*
        SendButton = findViewById<FloatingActionButton>(R.id.fab)
        if (SendButton != null) {
            SendButton.setOnClickListener(
                View.OnClickListener
                //This method runs everytime the decrypt button is clicked
                { //Log statement to assure us that we have gotten here
                    Log.d("Ran", "Sending")
                    //Toast.makeText(this, "Sending text..." + findViewById(R.id.input).toString(), Toast.LENGTH_SHORT).show();
                    val input = findViewById<View>(R.id.input) as EditText
                    FirebaseDatabase.getInstance().reference.push().setValue(
                        ChatMessage(
                            input.text.toString(), FirebaseAuth.getInstance().currentUser!!
                                .email
                        )
                    )
                    input.setText("")
                })
        }
        */
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            previewImage.setImageURI(imageUri)
        }
        else if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val googleSignInAccount = task.getResult(ApiException::class.java)
                firebaseSignInWithGoogle(googleSignInAccount)
            } catch (e: ApiException) {
                makeText(this, "Google sign in exception", LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseSignInWithGoogle(googleSignInAccount: GoogleSignInAccount) {
        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        firebaseAuth!!.signInWithCredential(authCredential).addOnCompleteListener(this) {
            val firebaseUser = firebaseAuth!!.currentUser
            if (firebaseUser != null) {
                createUserIfNotExists(firebaseUser)
            }
        }
        makeText(this, "Google sign in success", LENGTH_SHORT).show()
    }

    private fun createUserIfNotExists(firebaseUser: FirebaseUser) {
        val uid = firebaseUser.uid
        val userName = firebaseUser.displayName
        val user = User(uid, userName!!)

        val uidRef = rootRef!!.collection("users").document(uid)
        uidRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.exists()) {
                    uidRef.set(user)
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        firebaseAuth!!.addAuthStateListener(authStateListener!!)
    }

    public override fun onStop() {
        super.onStop()
        if (firebaseAuth != null) {
            firebaseAuth!!.removeAuthStateListener(authStateListener!!)
        }
    }

    companion object {
        //private val TAG = "LoginActivity"
        const val RC_SIGN_IN = 123
    }

    fun onClick(view: android.view.View) {
        /*
        etName
        if(etName.text.isNullOrEmpty())
        {
            Toast.makeText(this,"Could you please sign in or register? " , Toast.LENGTH_LONG).show();
        }
        */
        Toast.makeText(this,"Welcome now listing hospitals", Toast.LENGTH_LONG).show();
        //setContentView(R.layout.activity_item_detail)
        val intent = Intent(this, RecyclerActivity::class.java)
        startActivity(intent)
        /*
        when (v.getId()) {
            com.example.detailapplication.R.id.sign_in_button -> signIn()
        }
        */
    }
}
