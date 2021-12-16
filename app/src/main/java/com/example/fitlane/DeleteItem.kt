package com.example.fitlane

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.fitlane.databinding.FragmentDeleteItemBinding
import com.example.fitlane.databinding.FragmentSelectItemBinding
import com.google.firebase.firestore.FirebaseFirestore

class DeleteItem : Fragment(R.layout.fragment_delete_item) {

    private var _binding : FragmentDeleteItemBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private lateinit var myBox: AlertDialog.Builder


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDeleteItemBinding.inflate(inflater, container, false)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var title = requireArguments().getString("title").toString()
        var mealName = requireArguments().getString("mealName").toString()
        val id = requireArguments().getString("id").toString()
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



        //deleting meal

        myBox = AlertDialog.Builder(getActivity())
        binding.deleteItem.setOnClickListener {

            myBox.setTitle("Alert!").
            setMessage("Are you sure you want to remove this item?").
            setCancelable(true).
            setPositiveButton("Yes"){dialogInterfce, it ->

                db.collection("my_meals").document(title).collection("meal_items").document("jxAz67E0mzL0qmeEcKut") //fixa så att man vet meal items document man ska ta bort
                    .delete()
                    .addOnSuccessListener {

                        Toast.makeText(getActivity(),"deleted " + id.toString() + " sucsussfully",Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(getActivity(),"Error deleting " + title,Toast.LENGTH_SHORT).show()
                    }
            }
                .setNegativeButton("No"){dialogInterface, it ->
                    dialogInterface.cancel()
                }.show()


        }




    }






}