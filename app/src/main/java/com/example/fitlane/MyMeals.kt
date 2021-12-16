package com.example.fitlane

import android.app.AlertDialog
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
import androidx.core.os.bundleOf
import com.example.fitlane.databinding.CreateMealBinding
import com.example.fitlane.databinding.NutritionHomepageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.fitlane.databinding.MyMealsBinding


class MyMeals : Fragment(R.layout.my_meals) {

    private var _binding : MyMealsBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    var mealList = mutableListOf<my_meals_model>()
    private lateinit var myBox: AlertDialog.Builder
    var title = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = MyMealsBinding.inflate(inflater, container, false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = requireArguments().getString("title").toString()
        val mealId = requireArguments().getLong("mealId")
        binding.something.text = title.toString()




        Toast.makeText(getActivity(), title.toString(),Toast.LENGTH_SHORT).show();


        binding.addItem.setOnClickListener {


            //findNavController().navigate(R.id.go_to_add_item)
            val addItems = AddItems()
            val args = Bundle()
            args.putLong("mealId", mealId)
            args.putString("title", title)
            addItems.setArguments(args)
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.bottom_nav_fragment, addItems).addToBackStack(null).commit()
        }


        //deleting meal
        myBox = AlertDialog.Builder(getActivity())


        binding.deleteMeal.setOnClickListener {

            myBox.setTitle("Alert!").
                setMessage("Are you sure you want to remove this item?").
                setCancelable(true).
                setPositiveButton("Yes"){dialogInterfce, it ->

                    db.collection("my_meals").document(title)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(getActivity(),"deleted " + title + " sucsussfully",Toast.LENGTH_SHORT).show()
                            val createMeal = CreateMeal()
                            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                            transaction.replace(R.id.bottom_nav_fragment, createMeal).addToBackStack(null).commit()
                        }
                        .addOnFailureListener {
                            Toast.makeText(getActivity(),"Error deleting " + title,Toast.LENGTH_SHORT).show()
                        }
                }
                .setNegativeButton("No"){dialogInterface, it ->
                    dialogInterface.cancel()
                }.show()





        }


        binding.myMealsList.setOnItemClickListener (AdapterView.OnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->




            Toast.makeText(getActivity(),mealList[position].title,Toast.LENGTH_SHORT).show()

            val deleteItem = DeleteItem()
            val args = Bundle()
            args.putString("title", mealList[position].title)
            args.putLong("calories", mealList[position].description as Long)
            args.putString("id", mealList[position].calories.toString())
            args.putString("mealName", title)
            deleteItem.setArguments(args)
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.bottom_nav_fragment, deleteItem, "a").addToBackStack(null).commit()


        })


        readMealItemList()



    }


    private fun readMealItemList(){

        db.collection("my_meals").document(title).collection("meal_items").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    mealList.add(my_meals_model(document.getString("my_item").toString(), document.getLong("Calories").toString().toLong(), 0, document.id ))

                }
                binding.myMealsList.adapter =
                    getActivity()?.let { my_meals_adapter(it, R.layout.my_meals_row, mealList) }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }



    }





}