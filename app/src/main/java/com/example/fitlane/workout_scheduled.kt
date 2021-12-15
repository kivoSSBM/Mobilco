package com.example.fitlane

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.fitlane.databinding.FragmentWorkoutScheduledBinding
import android.util.Log
import android.content.Intent
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class Workout_scheduled : Fragment(R.layout.fragment_workout_scheduled) {

    private var _binding: FragmentWorkoutScheduledBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("text", "on create view workout schedule")
        // Inflate the layout for this fragment
        _binding = FragmentWorkoutScheduledBinding.inflate(inflater, container, false)
        return  binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("text", "on view created workout schedule")
        Toast.makeText( getActivity(), "hej", Toast.LENGTH_SHORT).show()
        binding.abdominalsB.setOnClickListener{

            val userS:String = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val emailS:String = FirebaseAuth.getInstance().currentUser?.email.toString()
            //val aghil =
            var textS:String = "info:\n"
            //textS+= userS+"\n"
            //textS+= emailS+"\n"
            Toast.makeText( getActivity(), textS, Toast.LENGTH_SHORT).show()
            //print("aaaaa\n")
            //println("bbbb\n")
            //Log.d("text,""aa")
            Log.d("text", "aaaa")
            var t:Button = view.findViewById(R.id.abdominalsB)
            t.text= ("aaaa")
        }

    }


}