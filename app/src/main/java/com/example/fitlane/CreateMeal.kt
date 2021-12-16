package com.example.fitlane

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fitlane.databinding.CreateMealBinding
import com.example.fitlane.databinding.NutritionHomepageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CreateMeal : Fragment(R.layout.create_meal) {

    private var _binding : CreateMealBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = CreateMealBinding.inflate(inflater, container, false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        binding.createMealBtn.setOnClickListener{

            val input = binding.createMealInput.text.toString().trim()
            if (input.isNullOrBlank()) {

                Toast.makeText(getActivity(),"empty text!",Toast.LENGTH_SHORT).show();

            }

            else{
                createMeal()
            }


        }



        readMealList()








    }

    private  fun createMeal(){
        var currId: Long = 0

        db.collection("meal_id").document("id")
            .get().addOnSuccessListener { document ->
                if(document != null){
                    currId = document.getLong("id").toString().toLong() + 1
                    val id: MutableMap<String, Any> = HashMap()
                    id["id"] = currId
                    db.collection("meal_id").document("id").set(id)


                    val mealTitle = binding.createMealInput.text.toString()
                    val subField: MutableMap<String, Any> = HashMap()
                    subField["meal_desc"] = "---"
                    subField["id"] = currId
                    db.collection("my_meals").document(mealTitle).set(subField)
                    readMealList()
                }
            }
            .addOnFailureListener { exception ->

            }





    }

    private fun readMealList(){
        var mealList = mutableListOf<my_meals_model>()
        db.collection("my_meals").orderBy("id", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    mealList.add(my_meals_model(document.id, document.getString("meal_desc").toString()))

                }
                binding.myMealsList.adapter =
                    getActivity()?.let { my_meals_adapter(it, R.layout.my_meals_row, mealList) }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }




}