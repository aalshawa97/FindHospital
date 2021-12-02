package com.example.findhospital.ui.list

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

class HospitalListFragment : Fragment() {

    private lateinit var hospitalListViewModel: HospitalListViewModel
    private var _binding: FragmentHospitalListBinding? = null
    //private var intent :Intent =  Intent(activity,  RecyclerActivity::class.java)


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Timer("SettingUp", false).schedule(30000) {
        this@HospitalListFragment.startActivity(Intent(activity,  RecyclerActivity::class.java))
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