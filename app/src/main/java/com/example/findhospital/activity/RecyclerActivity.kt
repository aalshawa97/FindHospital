package com.example.findhospital.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findhospital.R
import com.example.findhospital.ui.list.Hospital
import com.example.findhospital.ui.list.MyAdapter

class RecyclerActivity : AppCompatActivity() {

    private lateinit var listAdapter: MyAdapter
    private val contactsList: ArrayList<Hospital> = ArrayList()
    private lateinit var recycler: RecyclerView
    private lateinit var makeCallButton : Button
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("5039359491")
        //startActivity(intent)

        setContentView(R.layout.recycler_view)
        //makeCallButton = findViewById(R.id.button_call);
        //Load the date from the network or other resources
        //into the array list asynchronously
        contactsList.add(Hospital("Providence Brigeport"))
        contactsList.add(Hospital("Providence Mercantile"))
        contactsList.add(Hospital("Providence St. Vincent"))
        contactsList.add(Hospital("Providence Newberg"))
        //listAdapter!!.notifyDataSetChanged()

        recycler = findViewById(R.id.my_recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recycler?.setLayoutManager(layoutManager)
        listAdapter = MyAdapter(contactsList, this)
        recycler.setAdapter(listAdapter)

        /*
        inline fun Timer.schedule(
            delay: Long,
            crossinline action: TimerTask.() -> Unit
        ): TimerTask
        */

        //

    }

    fun onLogin(view: android.view.View) {}
    fun makeCall(view: android.view.View) {
        Toast.makeText(this, "Phone calling", Toast.LENGTH_LONG).show()
        val intent = Intent(this, PhoneActivity::class.java)
        startActivity(intent)
    }
}