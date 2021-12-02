package com.example.findhospital.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.findhospital.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.Manifest;
import android.content.*
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.contacts_list_item.*
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.media.tv.TvContract.Channels.CONTENT_URI
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import androidx.annotation.RequiresApi
import java.lang.Exception
import android.content.pm.PackageManager
import android.media.tv.TvContract.Channels.Logo.CONTENT_DIRECTORY
import android.net.Uri
import android.provider.ContactsContract.Contacts
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
/*
import com.example.findhospital.activity.ContactsActivity.Companion.PERMISSIONS_REQUEST_READ_CONTACTS
class ContactsActivity: AppCompatActivity() {
    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }
    lateinit var aName : EditText
    lateinit var aEmail : EditText
    lateinit var aPhone : EditText
    var list_viw : ListView? = null
    var button : Button? = null
    //var anAddContact = findViewById<Button>(R.id.btnAdd)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list_viw = findViewById(R.id.list_viw)
        button = findViewById(R.id.button)
        setContentView(R.layout.contacts_list_item)
        aName = findViewById<EditText>(R.id.etName)
        aEmail = findViewById<EditText>(R.id.etEmail)
        aPhone = findViewById<EditText>(R.id.etPhone)
        val myIntent = Intent(this@ContactsActivity, ChatActivity::class.java)
        //this@ContactsActivity.startActivity(myIntent)
        val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
            // Sets the MIME type to match the Contacts Provider
            type = ContactsContract.RawContacts.CONTENT_TYPE
        }
        button.setOnClickListener(){
            if(!aName.text.isNullOrEmpty() && !etEmail.text.isNullOrEmpty() && !etPhone.text.isNullOrEmpty())
            {
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE)
                intent.putExtra(ContactsContract.Intents.Insert.NAME, aName.getText().toString());
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, aEmail.getText().toString());
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, aPhone.getText().toString());
                if(intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "There is no app that supports this action.", Toast.LENGTH_LONG).show()
                }
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Please fill all the fields",
                    Toast.LENGTH_SHORT).show();
                //textView4.setText(inputValue).toString()
            }
        }
        Toast.makeText(this, "Please add your contact", Toast.LENGTH_LONG).show()
        addContact()
        //addContact("Shahid Mohmammed", "2134368227")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = "Contacts"
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            val fromUid = firebaseUser.uid
            val rootRef = FirebaseFirestore.getInstance()
            val uidRef = rootRef.collection("users").document(fromUid)
            uidRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        val fromUser = document.toObject(User::class.java)
                        val userContactsRef = rootRef.collection("contacts").document(fromUid).collection("userContacts")
                        //First user
                        //rootRef.collection("contacts").document(fromUid).collection("userContacts").add("John Smith")
                        userContactsRef.get().addOnCompleteListener{ t ->
                            if (t.isSuccessful) {
                                val listOfToUserNames = ArrayList<String>()
                                val listOfToUsers = ArrayList<User>()
                                val listOfRooms = ArrayList<String>()
                                for (d in t.result) {
                                    val toUser = d.toObject(User::class.java)
                                    listOfToUserNames.add(toUser.userName)
                                    listOfToUsers.add(toUser)
                                    listOfRooms.add(d.id)
                                }
                                val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfToUserNames)
                                list_viw.adapter = arrayAdapter
                                list_viw.onItemClickListener = AdapterView.OnItemClickListener{_, _, position, _ ->
                                    val intent = Intent(this, ChatActivity::class.java)
                                    intent.putExtra("fromUser", fromUser)
                                    intent.putExtra("toUser", listOfToUsers[position])
                                    intent.putExtra("roomId", "noRoomId")
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
                    }
                }
            }
        }
        //loadContacts.setOnClickListener { loadContacts() }
    }
    private fun loadContacts() {
        var builder = StringBuilder()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS)
            //callback onRequestPermissionsResult
        } else {
            //builder = getContacts()
            val listContacts = ""
            //listContacts.text = builder.toString()
        }
    }
    private fun getPhoneContacts()
    {
        //if()
    }
    /*
    private fun getContacts(): StringBuilder {
        val builder = StringBuilder()
        val resolver: ContentResolver = contentResolver;
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
            null)
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber = (cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()
                if (phoneNumber > 0) {
                    val cursorPhone = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)
                    if (cursorPhone != null) {
                        if(cursorPhone.count > 0) {
                            while (cursorPhone.moveToNext()) {
                                val phoneNumValue = cursorPhone.getString(
                                    cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                builder.append("Contact: ").append(name).append(", Phone Number: ").append(
                                    phoneNumValue).append("\n\n")
                                Log.e("Name ===>",phoneNumValue);
                            }
                        }
                    }
                    cursorPhone.close()
                }
            }
        } else {
            //   toast("No contacts available!")
        }
        if (cursor != null) {
            cursor.close()
        }
        return builder
    }
}
*/
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            } else {
                //  toast("Permission must be granted in order to display contacts information")
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addContact() {
        val op_list = ArrayList<ContentProviderOperation>()
        op_list.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(
                    ContactsContract.RawContacts.ACCOUNT_NAME,
                    null
                ) //.withValue(RawContacts.AGGREGATION_MODE, RawContacts.AGGREGATION_MODE_DEFAULT)
                .build()
        )
        //First and last names
        op_list.add(
            ContentProviderOperation.newInsert(CONTENT_URI)
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(Contacts.Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.GIVEN_NAME, "Second Name")
                .withValue(StructuredName.FAMILY_NAME, "First Name")
                .build()
        )
        op_list.add(
            ContentProviderOperation.newInsert(CONTENT_URI)
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                .withValue(Phone.NUMBER, "09876543210")
                .withValue(Phone.TYPE, Phone.TYPE_HOME)
                .build()
        )
        op_list.add(
            ContentProviderOperation.newInsert(CONTENT_URI)
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                .withValue(Email.DATA, "abdullahalshawa@gmail.com")
                .withValue(Email.TYPE, Email.TYPE_WORK)
                .build()
        )
        try {
            val results = contentResolver.applyBatch(ContactsContract.AUTHORITY, op_list)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun addContacts(name: String, phone: String) {
        /*
        val values = ContentValues()
        values.put(Contacts.People.NUMBER, phone)
        values.put(Contacts.People.TYPE, Phone.TYPE_CUSTOM)
        values.put(Contacts.People.LABEL, name)
        values.put(Contacts.People.NAME, name)
        val dataUri = contentResolver.insert(Contacts.People.CONTENT_URI, values)
        var updateUri = Uri.withAppendedPath(dataUri, Contacts.People.Phones.CONTENT_DIRECTORY)
        values.clear()
        values.put(Contacts.Phones.TYPE, Contacts.People.TYPE_MOBILE)
        values.put(Contacts.People.NUMBER, phone)
        updateUri = contentResolver.insert(updateUri!!, values)
        Log.d("CONTACT", "" + updateUri)
        */
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addContact(name: String, phone: String) {
        val values = ContentValues()
        values.put(Phone.CONTENT_ITEM_TYPE, phone)
        values.put(Contacts.CONTENT_TYPE, Phone.TYPE_CUSTOM)
        values.put(Contacts.CONTACT_STATUS_LABEL, name)
        values.put(Contacts.DISPLAY_NAME, name)
        val dataUri: Uri? = contentResolver.insert(CONTENT_URI, values)
        var updateUri: Uri? =
            Uri.withAppendedPath(dataUri, Phone.IN_DEFAULT_DIRECTORY)
        values.clear()
        values.put(Phone.CONTENT_ITEM_TYPE, Phone.TYPE_MOBILE)
        values.put(Phone.CONTENT_ITEM_TYPE, phone)
        updateUri = updateUri?.let { contentResolver.insert(it, values) }
        Log.d("CONTACT", "" + updateUri)
    }
    /*
    public fun fragment_transaction(fragment : Fragment)
    {
        //setContentView(R.layout.contacts_list_item)
        /*
        FragmentTransaction transaction = getSupportFragmentManager ()
            .beginTransaction()
            .replace(R.id.drawer_layout, mFragment);   // < - first problem ( see ad. 1)
        .commit()  // <-  third problem (see ad. 3)
        // second problem ( see ad. 2)
        mFragment = new AnnouncementFragment ();
        Bundle args = new Bundle();
        args.putInt(null, position);
        */
    }
    */
    /*
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
    Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
            PERMISSIONS_REQUEST_READ_CONTACTS)
        //callback onRequestPermissionsResult
    } else {
        builder = getContacts()
        listContacts.text = builder.toString()
    }
}
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                        grantResults: IntArray) {
    if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadContacts()
        } else {
            //  toast("Permission must be granted in order to display contacts information")
        }
    }
}
*/
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }
    }
}
*/