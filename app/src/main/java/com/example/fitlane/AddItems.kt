package com.example.fitlane

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.example.fitlane.databinding.FragmentAddItemsBinding
import com.google.firebase.firestore.FirebaseFirestore
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager


class AddItems : Fragment(R.layout.meal_search_bar) {

    private var _binding : FragmentAddItemsBinding? = null
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()
    var itemList = mutableListOf<my_meals_model>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddItemsBinding.inflate(inflater, container, false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = requireArguments().getString("title").toString()
        val mealId = requireArguments().getLong("mealId")

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{


            override fun onQueryTextSubmit(query: String): Boolean {
                binding.searchView.clearFocus()
                if(itemList.contains<Any>(query)){
                    //binding.myItemsList.adapter =
                        //getActivity()?.let { my_meals_adapter(it, R.layout.my_meals_row, itemList).filter.filter() }


                }
                return false



            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }


        })




        binding.myItemsList.setOnItemClickListener (AdapterView.OnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->




            val selectItem = SelectItem()
            val args = Bundle()
            args.putString("title", itemList[position].title)
            args.putLong("calories", itemList[position].description as Long)
            args.putString("mealName", title)
            selectItem.setArguments(args)
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.bottom_nav_fragment, selectItem, "B").addToBackStack(null).commit()

            //adding the meal items
            //val meal_items1: MutableMap<String, Any> = HashMap()
            //meal_items1["id"] = itemList[position].id
            //meal_items1["calories"] = itemList[position].description    //this is the calories
            //meal_items1["my_item"] = itemList[position].title

            //Toast.makeText(getActivity(), itemList[position].title + " added successfully to list", Toast.LENGTH_SHORT).show();
            //db.collection("my_meals").document(title).collection("meal_items").document().set(meal_items1)



        })

        readMealItemList()




    }





    private fun readMealItemList(){

        db.collection("items").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    itemList.add(my_meals_model(document.id.toString(), document.getLong("Calories").toString().toLong(), 0, document.id ))

                }
                binding.myItemsList.adapter =
                    getActivity()?.let { my_meals_adapter(it, R.layout.my_meals_row, itemList) }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }



    }




}