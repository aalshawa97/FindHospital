package com.example.findhospital.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64.encodeToString
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.findhospital.R
import com.example.findhospital.activity.ChatActivity.AESEncyption.encrypt
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
//import kotlinx.android.synthetic.main.activity_chat.*
import java.lang.Byte.decode
import java.security.NoSuchAlgorithmException
import java.security.spec.PSSParameterSpec.DEFAULT
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class ChatActivity: AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var listView: ListView
    private var rootRef: FirebaseFirestore? = null
    private var fromUid: String? = ""
    private var adapter: MessageAdapter? = null
    private lateinit var recycler_view  : RecyclerView
    //private val encryptionKey: IntArray = intArrayOf(5, 115, 51, 86, 105, 4, -31, -23, -60, 80, 17, 20, 3, -105, -53)
    //private var cipher: Cipher = Cipher.getInstance("<i>DES/CBC/PKCS5Padding</i>")
    //var d: Decipher = null
    //private SecretKeySpec secretKeySpec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val fromUser = intent.extras?.get("fromUser") as User
        fromUid = fromUser.uid
        var fromRooms = fromUser.rooms
        val toUser = intent.extras?.get("toUser") as User
        val toUid = toUser.uid
        var toRooms = toUser.rooms

        var roomId = intent.extras?.get("roomId") as String

        if (roomId == "noRoomId") {
            roomId = rootRef!!.collection("messages").document().id
            if (fromRooms != null) {
                for ((key, _) in fromRooms) {
                    if (toRooms != null) {
                        if (toRooms.contains(key)) {
                            roomId = key
                        }
                    }
                }
            }
        }

        val button = findViewById<Button>(R.id.button)
        val edit_text = findViewById<EditText>(R.id.edit_text)

        button.setOnClickListener {
            if (fromRooms == null) {
                fromRooms = mutableMapOf()
            }
            fromRooms!![roomId] = true
            fromUser.rooms = fromRooms
            rootRef!!.collection("users").document(fromUid!!).set(fromUser, SetOptions.merge())
            rootRef!!.collection("contacts").document(toUid).collection("userContacts").document(fromUid!!).set(fromUser, SetOptions.merge())
            rootRef!!.collection("rooms").document(toUid).collection("userRooms").document(roomId).set(fromUser, SetOptions.merge())

            if (toRooms == null) {
                toRooms = mutableMapOf()
            }
            toRooms!![roomId] = true
            toUser.rooms = toRooms
            rootRef!!.collection("users").document(toUid).set(toUser, SetOptions.merge())
            rootRef!!.collection("contacts").document(fromUid!!).collection("userContacts").document(toUid).set(toUser, SetOptions.merge())
            rootRef!!.collection("rooms").document(fromUid!!).collection("userRooms").document(roomId).set(toUser, SetOptions.merge())

            val messageText = edit_text.text.toString()
            val message = Message(messageText, fromUid!!)
            rootRef!!.collection("messages").document(roomId).collection("roomMessages").add(message)
            edit_text.text.clear()
        }

        val query = rootRef!!.collection("messages").document(roomId).collection("roomMessages").orderBy("sentAt", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<Message>().setQuery(query, Message::class.java).build()
        adapter = MessageAdapter(options)
        recycler_view.adapter = adapter

        title = toUser.userName

        //Testing RSA
        encrypt("This is secret!")

        try {
            //cipher = Cipher.getInstance("RSA")
            val keyBytes = byteArrayOfInts(0xA1, 0x2E, 0x38, 0xD4, 0x89, 0xC3)
            val secretKey: SecretKey = SecretKeySpec(keyBytes, "AES")
            //cipher.init()
            //decipher = Cipher.getInstance("AES")
        }
        catch(e: NoSuchAlgorithmException)
        {
            e.printStackTrace()
        }

        editText = findViewById(R.id.edit_text)
        listView = findViewById(R.id.list_viw)
        rootRef = FirebaseFirestore.getInstance()
        recycler_view = findViewById(R.id.recycler_view)


        //secretKeySpec = new SecreyKeySpec()
    }

    object AESEncyption {

        const val secretKey = "tK5UTui+DPh8lIlBxya5XVsmeDCoUl6vHhdIESMB6sQ="
        const val salt = "QWlGNHNhMTJTQWZ2bGhpV3U=" // base64 decode => AiF4sa12SAfvlhiWu
        const val iv = "bVQzNFNhRkQ1Njc4UUFaWA==" // base64 decode => mT34SaFD5678QAZX

        fun encrypt(strToEncrypt: String) :  String?
        {
            try
            {
                Log.d("ChatActivity", "Before encrypting: RSA " + strToEncrypt)

                val ivParameterSpec = IvParameterSpec(android.util.Base64.decode(iv, android.util.Base64.DEFAULT))

                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val spec =  PBEKeySpec(secretKey.toCharArray(), android.util.Base64.decode(salt, android.util.Base64.DEFAULT), 10000, 256)
                val tmp = factory.generateSecret(spec)
                val secretKey =  SecretKeySpec(tmp.encoded, "AES")

                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
                //Toast.makeText(this, "Opening chat", Toast.LENGTH_LONG).show()
                Log.d("ChatActivity", "Encrypted: RSA " + android.util.Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)), android.util.Base64.DEFAULT))

                //this@ChatActivity.startActivity(new Intent(this@ChatActivitiy, RSA::class.java))
                //Toast.makeText(this, "Testing encryption with RSA", Toast.LENGTH_LONG).show()
                return android.util.Base64.encodeToString(cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)), android.util.Base64.DEFAULT)
            }
            catch (e: Exception)
            {
                println("Error while encrypting: $e")
            }
            return null
        }

        fun decrypt(strToDecrypt : String) : String? {
            try
            {

                /*
                val ivParameterSpec =  IvParameterSpec(android.util.Base64.(iv, android.util.Base64.DEFAULT))

                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                val spec =  PBEKeySpec(secretKey.toCharArray(), android.util.Base64.decode(salt, android.util.Base64.DEFAULT), 10000, 256)
                val tmp = factory.generateSecret(spec);
                val secretKey =  SecretKeySpec(tmp.encoded, "AES")

                val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
                return  String(cipher.doFinal(android.util.Base64.decode(strToDecrypt, android.util.Base64.DEFAULT)))
                */
            }
            catch (e : Exception) {
                println("Error while decrypting: $e");
            }
            return null
        }
    }

    fun byteArrayOfInts(vararg ints: Int) = ByteArray(ints.size) { pos -> ints[pos].toByte() }


    inner class MessageViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setMessage(message: Message) {
            val textView = view.findViewById<TextView>(R.id.text_view)
            //We can encrypt here...
            textView.text = message.messageText
        }
    }

    inner class MessageAdapter internal constructor(options: FirestoreRecyclerOptions<Message>) : FirestoreRecyclerAdapter<Message, MessageViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            return if (viewType == R.layout.item_message_to) {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_to, parent, false)
                MessageViewHolder(view)
            } else {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_from, parent, false)
                MessageViewHolder(view)
            }
        }

        override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
            holder.setMessage(model)
        }

        override fun getItemViewType(position: Int): Int {
            return if (fromUid != getItem(position).fromUid) {
                R.layout.item_message_to
            } else {
                R.layout.item_message_from
            }
        }

        override fun onDataChanged() {
            recycler_view.layoutManager?.scrollToPosition(itemCount - 1)
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(menuItem)
        }
    }

    override fun onStart() {
        super.onStart()

        if (adapter != null) {
            adapter!!.startListening()
        }
    }

    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }
    }
}