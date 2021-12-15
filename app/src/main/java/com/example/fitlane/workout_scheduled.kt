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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [workout_scheduled.newInstance] factory method to
 * create an instance of this fragment.
 */
class workout_scheduled : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentWorkoutScheduledBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWorkoutScheduledBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
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

            binding.newWorkBtn.setOnClickListener {
                findNavController().navigate(R.id.action_select_workout_one)
            }
        }

    }


}