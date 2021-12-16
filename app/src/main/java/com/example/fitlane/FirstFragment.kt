package com.example.fitlane

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fitlane.databinding.FragmentFirstBinding

import android.app.Service
//import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.util.*
/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.TimerStart.setOnClickListener{
            Toast.makeText( activity, "hej", Toast.LENGTH_SHORT).show()
            val userS:String = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val emailS:String = FirebaseAuth.getInstance().currentUser?.email.toString()
            var textS:String = "info:\n"
            textS+= userS+"\n"
            textS+= emailS+"\n"
            Toast.makeText( getActivity(), textS, Toast.LENGTH_SHORT).show()

            Log.d("text", "aaaa")
            var t: Button = view.findViewById(R.id.Timer_start)
            t.text= ("aaaa")

        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}