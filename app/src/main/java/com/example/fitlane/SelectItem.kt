package com.example.fitlane

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.fitlane.databinding.FragmentSelectItemBinding
import com.example.fitlane.databinding.MyMealsBinding
import com.google.firebase.firestore.FirebaseFirestore

class SelectItem : Fragment(R.layout.fragment_select_item) {

    private var _binding : FragmentSelectItemBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSelectItemBinding.inflate(inflater, container, false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var title = requireArguments().getString("title").toString()
        var mealName = requireArguments().getString("mealName").toString()
        val mealId = requireArguments().getLong("mealId")
        val cal = requireArguments().getLong("calories")


        db.collection("items").document(title)
            .get().addOnSuccessListener { document ->
                if(document != null){
                    binding.itemTitle.text = document.id
                    binding.itemDesc.text = document.getString("desc").toString()
                    binding.itemCalories.text = document.getLong("Calories").toString()

                }
            }
            .addOnFailureListener { exception ->

            }



        binding.addItemToList.setOnClickListener {
            //adding the meal items
            val meal_items1: MutableMap<String, Any> = HashMap()
            meal_items1["Calories"] = cal    //this is the calories
            meal_items1["my_item"] = binding.itemTitle.text

            Toast.makeText(getActivity(), binding.itemTitle.text.toString() + " added successfully to list", Toast.LENGTH_SHORT).show();
            db.collection("my_meals").document(mealName).collection("meal_items").document().set(meal_items1)
        }



        Toast.makeText(getActivity(), title.toString(), Toast.LENGTH_SHORT).show();


    }






}