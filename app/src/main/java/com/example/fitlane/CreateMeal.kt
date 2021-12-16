package com.example.fitlane

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import com.example.fitlane.databinding.CreateMealBinding
import com.example.fitlane.databinding.NutritionHomepageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation


class CreateMeal : Fragment(R.layout.create_meal) {

    private var _binding : CreateMealBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    var mealList = mutableListOf<my_meals_model>()

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

                Toast.makeText(getActivity(), "Empty Fields!", Toast.LENGTH_SHORT).show();

            }

            else{
                createMeal()

            }

        }



        binding.myMealsList.setOnItemClickListener(OnItemClickListener { parent:AdapterView<*>, view:View, position:Int, id:Long ->

            val myMeals = MyMeals()
            val args = Bundle()
            args.putLong("mealId", mealList[position].id)
            args.putString("title", mealList[position].title)
            myMeals.setArguments(args)
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.bottom_nav_fragment, myMeals).addToBackStack(null).commit()


        })





        readMealList()








    }

    private  fun createMeal(){
        var currId: Long = 0

        //adding the meal items



        mealList = mutableListOf<my_meals_model>()

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
                    Toast.makeText(getActivity(), "$mealTitle Was Created Successfully", Toast.LENGTH_SHORT).show();
                    readMealList()

                }
            }
            .addOnFailureListener { exception ->

            }







    }

    private fun readMealList(){




        db.collection("my_meals").orderBy("id", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { result ->


                for (document in result) {

                    var allItems:String = ""
                    db.collection("my_meals").document(document.id).collection("meal_items").get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                if(document != null){
                                    allItems += document.getString("my_item").toString() + ","
                                }
                                else{
                                    allItems = "---"
                                }


                            }

                            if(allItems == ""){
                                allItems = "---"
                            }
                            //adding all items here
                            mealList.add(my_meals_model(document.id, allItems, document.getLong("id").toString().toLong(), 0))
                            binding.myMealsList.adapter =
                                getActivity()?.let { my_meals_adapter(it, R.layout.my_meals_row, mealList) }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "Error getting documents: ", exception)
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }

}