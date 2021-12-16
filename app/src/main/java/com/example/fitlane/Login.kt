package com.example.fitlane

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fitlane.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */
class Login : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        //val regbtn = findViewById<TextView>(R.id.sign_up)
        binding.signUp.setOnClickListener{
            val username:String = view.findViewById<TextView>(R.id.editTextTextEmailAddress).text.toString().trim(){it<= ' '}
            val password:String = view.findViewById<TextView>(R.id.editTextTextPassword).text.toString().trim(){it<= ' '}
            when
            {
                TextUtils.isEmpty(username) && TextUtils.isEmpty(password) ->
                {
                    Toast.makeText(activity, "Enter email and password", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(username) ->
                {
                    Toast.makeText(activity, "Enter email", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(password) ->
                {
                    Toast.makeText(activity, "Enter password", Toast.LENGTH_SHORT).show()
                }
                else ->
                {
                    auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener() { task ->
                        if (task.isSuccessful)
                        {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            Toast.makeText(activity, "Success Register!!", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            val f:String = "Fail Reg, to short password or invalid email"
                            Toast.makeText(activity, f, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        binding.loginB.setOnClickListener {
            val username:String = view.findViewById<TextView>(R.id.editTextTextEmailAddress).text.toString().trim(){it<= ' '}
            val password:String = view.findViewById<TextView>(R.id.editTextTextPassword).text.toString().trim(){it<= ' '}
            when
            {
                TextUtils.isEmpty(username) && TextUtils.isEmpty(password) ->
                {
                    Toast.makeText(activity, "Enter email and password", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(username) ->
                {
                    Toast.makeText(activity, "Enter email", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(password) ->
                {
                    Toast.makeText(activity, "Enter password", Toast.LENGTH_SHORT).show()
                }
                else->
                {
                    auth.signInWithEmailAndPassword(username, password).addOnCompleteListener() { task ->
                        if (task.isSuccessful)
                        {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            Toast.makeText(activity, "Success Loooogin!!", Toast.LENGTH_SHORT).show()
                            /* val intent = Intent(this@MainActivity,MainActivity::class.java)
                             intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                             intent.putExtra("user_id",firebaseUser.uid)
                             intent.putExtra("email_id",username)
     *                        */
                            //findNavController().navigate(R.id.action_Login_to_Menu)
                            //setContentView(R.layout.fragment_workout_scheduled)
                            findNavController().navigate(R.id.action_Login_to_Menu)

                        }
                        else
                        {
                            Toast.makeText(activity, "Fail!!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.skipB.setOnClickListener{
            findNavController().navigate(R.id.action_Login_to_Menu)
        }
        binding.forgorPassword.setOnClickListener {
            Toast.makeText(activity, "You forgot?? Too bad!!", Toast.LENGTH_SHORT).show()
        }

        /*val logoffbtn = findViewById<Button>(R.id.logoutB)
        loginbtn.setOnClickListener {
            Toast.makeText(activity, "Logging out..", Toast.LENGTH_SHORT).show()
            auth.signOut()
        }*/
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Login.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Login().apply {
                arguments = Bundle().apply {

                }
            }
    }
}