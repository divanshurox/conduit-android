package com.example.conduit

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.api.models.entities.User
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    companion object {
        const val SHARED_PREF_AUTH = "prefs_auth"
        const val PREF_TOKEN = "token"
    }
    private lateinit var authViewModel: AuthViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: NavigationView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences(SHARED_PREF_AUTH, Context.MODE_PRIVATE)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_global_feed,
            R.id.nav_user_feed,
            R.id.nav_auth
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        sharedPreferences.getString(PREF_TOKEN,null)?.let{ t ->
            authViewModel.getUser(t)
        }
        authViewModel.user.observe({lifecycle}){
            updateMenu(it)
            it?.token?.let{t ->
                sharedPreferences.edit {
                    putString(PREF_TOKEN,t)
                }
            } ?: run {
                sharedPreferences.edit{
                    remove(PREF_TOKEN)
                }
            }
            navController.navigateUp()
        }
    }


    private fun updateMenu(user: User?){
        navView.menu.clear()
        when(user){
            is User -> {
                navView.inflateMenu(R.menu.activity_main_user)
            }
            else -> {
                navView.inflateMenu(R.menu.activity_main_guest)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout -> {
                authViewModel.logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}