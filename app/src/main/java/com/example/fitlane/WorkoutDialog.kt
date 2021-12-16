package com.example.fitlane

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.fitlane.databinding.FragmentDialogBinding
import com.example.fitlane.databinding.WorkoutHomepageBinding

class WorkoutDialog : DialogFragment() {

    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogBinding.inflate(inflater, container, false)

       // var rootView: View = inflater.inflate(R.layout.fragment_dialog, container, false)

        return binding.root
       // return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.quickstart.setOnClickListener {
            findNavController().navigate(R.id.action_select_workout_one)

        }

    }

}
