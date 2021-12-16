package com.example.fitlane

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.fitlane.databinding.NutritionHomepageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase





class Nutrition : Fragment(R.layout.nutrition_homepage) {
  
    private var _binding : NutritionHomepageBinding? = null
    private val binding get() = _binding!!

  

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = NutritionHomepageBinding.inflate(inflater, container, false)
        return  binding.root
        
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readCalories()





        binding.accept.setOnClickListener {
            val input = binding.currHeight.text.toString().trim()
            val input1 = binding.currWeight.text.toString().trim()
            val input2 = binding.goalWeight.text.toString().trim()
            if (input.isNullOrBlank() || input2.isNullOrBlank() || input1.isNullOrBlank()) {
                Toast.makeText(getActivity(), "Empty Fields!", Toast.LENGTH_SHORT).show();
            }

            else{
                goalWeight()
                readCalories()
            }

        }




        binding.addMeal.setOnClickListener(){

            findNavController().navigate(R.id.go_to_create_meal)
        }

                         
        






    }






    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    private fun goalWeight(){
        val currHeight = binding.currHeight.text.toString().toInt()
        val currWeight = binding.currWeight.text.toString().toInt()
        val gWeight = binding.goalWeight.text.toString().toInt()
        val neededCalories = (currHeight * 4) + (currWeight * 25) + (gWeight * 5)
        val db = FirebaseFirestore.getInstance()
        val goal: MutableMap<String, Any> = HashMap()
        goal["currHeight"] = currHeight
        goal["currWeight"] = currWeight
        goal["goalWeight"] = gWeight
        goal["neededCalories"] = neededCalories

        db.collection("calories").document("goal").set(goal)
    }


    private fun readCalories(){
        val db = FirebaseFirestore.getInstance()
        db.collection("calories").document("goal")
            .get().addOnSuccessListener { document ->
                if(document != null){
                    binding.dailyCalories.text = document.getLong("neededCalories").toString()
                }
            }
            .addOnFailureListener { exception ->

            }

    }






}