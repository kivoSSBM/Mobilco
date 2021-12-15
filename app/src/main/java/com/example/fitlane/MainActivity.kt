package com.example.fitlane

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.ui.setupWithNavController

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.fitlane.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        val navController = findNavController(R.id.bottom_nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        } */

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val bottomNavController = findNavController(R.id.bottom_nav_fragment)

        bottomNavigationView.setupWithNavController(bottomNavController)

        // setContentView(R.layout.fragment_exeercise_categories)
        //setContentView(R.layout.fragment_workout_scheduled)

        //setContentView(R.layout.fragment_login)

        /* setContentView(R.layout.create_meal)

        val search = findViewById<SearchView>(R.id.searchView)
        val listView = findViewById<ListView>(R.id.listView)

        val names = arrayOf("apple", "chicken", "meat", "bannana", "fish", "fries")

        val adapter:ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1
            , names
        )

        listView.adapter= adapter

        search.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                search.clearFocus()
                if (names.contains(p0))
                {
                    adapter.filter.filter(p0)
                }else{
                    Toast.makeText(applicationContext,"Item not found", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }

        }) */
        auth = Firebase.auth

        val regbtn = findViewById<TextView>(R.id.sign_up)
        regbtn.setOnClickListener{
            val username:String = findViewById<TextView>(R.id.editTextTextEmailAddress).text.toString().trim(){it<= ' '}
            val password:String = findViewById<TextView>(R.id.editTextTextPassword).text.toString().trim(){it<= ' '}
            when
            {
                TextUtils.isEmpty(username) ->
                {
                    val t: String = "enter user"
                    val text:String = t+username
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(password) ->
                {
                    Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                }
                else ->
                {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener() { task ->
                            if (task.isSuccessful)
                            {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(this, "Success Register!!", Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                val f:String = "Fail Reg, to short password or invalid email"
                                Toast.makeText(this, f, Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
        val loginbtn = findViewById<Button>(R.id.loginB)
        loginbtn.setOnClickListener {
            val username:String = findViewById<TextView>(R.id.editTextTextEmailAddress).text.toString().trim(){it<= ' '}
            val password:String = findViewById<TextView>(R.id.editTextTextPassword).text.toString().trim(){it<= ' '}
            when
            {
                TextUtils.isEmpty(username) && TextUtils.isEmpty(password) ->
                {
                    Toast.makeText(this, "enter email and password", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(username) ->
                {
                    Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(password) ->
                {
                    Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
                }
                else->
                {
                    auth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful)
                            {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(this, "Success Login!!", Toast.LENGTH_SHORT).show()
                                /* val intent = Intent(this@MainActivity,MainActivity::class.java)
                                 intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                 intent.putExtra("user_id",firebaseUser.uid)
                                 intent.putExtra("email_id",username)
         *                        */
                                //findNavController().navigate(R.id.action_Login_to_Menu)
                                //setContentView(R.layout.fragment_workout_scheduled)

                            }
                            else
                            {
                                Toast.makeText(this, "Fail!!", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }

        }

        /*val logoffbtn = findViewById<Button>(R.id.logoutB)
        loginbtn.setOnClickListener {
            auth.signOut()
        }*/







    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
//            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
