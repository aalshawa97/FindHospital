package com.example.findhospital.ui.chat

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.findhospital.R
import com.example.findhospital.activity.*
import com.example.findhospital.databinding.FragmentNotificationsBinding
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.concurrent.schedule

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    lateinit var editText: EditText
    lateinit var listView: ListView
    private var rootRef: FirebaseFirestore? = null
    private var fromUid: String? = ""
    private var adapter: MessageAdapter? = null
    private lateinit var recycler_view  : RecyclerView
    //private var intent : Intent =  Intent(activity,  ChatActivity::class.java)


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Timer("SettingUp", false).schedule(30000) {
        //setContentView(R.layout.activity_chat)

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        this@NotificationsFragment.startActivity(Intent(activity, MainActivityJ::class.java))
        //this@NotificationsFragment.startActivity(Intent(activity, ChatActivity::class.java))
        //}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        try {
            getActivity()?.getIntent()?.getExtras()?.getString("fromUser") as User
            //val fromUser = intent.extras?.get("fromUser") as User
            val fromUser = getActivity()?.getIntent()?.getExtras()?.getString("fromUser") as User

            fromUid = fromUser.uid
            var fromRooms = fromUser.rooms
            //val toUser = intent.extras?.get("toUser") as User
            val toUser = getActivity()?.getIntent()?.getExtras()?.getString("toUser") as User

            val toUid = toUser.uid
            var toRooms = toUser.rooms

            //var roomId = intent.extras?.get("roomId") as String
            var roomId = getActivity()?.getIntent()?.getExtras()?.getString("roomId") as String

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
            //= (ImageView) getView().findViewById(R.id.foo);
            val view: View = inflater.inflate(R.layout.activity_chat, container, false)
            val button = view.findViewById<View>(R.id.button) as Button
            val edit_text = view.findViewById<View>(R.id.edit_text) as EditText

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
            var title : String? = null
            title = toUser.userName

            //Testing RSA
            ChatActivity.AESEncyption.encrypt("This is secret!")

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

            editText = view.findViewById<View>(R.id.edit_text) as EditText
            listView = view.findViewById<View>(R.id.list_viw) as ListView
            rootRef = FirebaseFirestore.getInstance()
            recycler_view = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        }
        catch (e : java.lang.Exception)
        {
            Log.d(ContentValues.TAG, "onCreate: exception thrown when getting user")
        }

        return root
    }

    sealed class SealedClassSuper {
        abstract class SealedClassChild(val x: String): SealedClassSuper()
    }

    class OuterClass {
        inner class InnerClassX(x: String) : SealedClassSuper.SealedClassChild(x)

        fun someFun(): SealedClassSuper {
            return InnerClassX("Hello")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class MessageViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setMessage(message: Message) {
            val textView = view.findViewById<TextView>(R.id.text_view)
            //We can encrypt here...
            textView.text = message.messageText
        }
    }


    fun byteArrayOfInts(vararg ints: Int) = ByteArray(ints.size) { pos -> ints[pos].toByte() }


    inner class MessageAdapter internal constructor(options: FirestoreRecyclerOptions<Message>) : FirestoreRecyclerAdapter<Message, MessageViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
            return if (viewType == R.layout.item_message_to) {
                //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_to, parent, false)

                MessageViewHolder(
                    view?.findViewById<View>(R.id.text_view) as View
                )
            } else {
                //val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_from, parent, false)
                MessageViewHolder(view?.findViewById<View>(R.id.text_view) as View)
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
                //finish()
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