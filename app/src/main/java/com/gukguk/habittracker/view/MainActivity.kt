package com.gukguk.habittracker.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.gukguk.habittracker.R
import com.gukguk.habittracker.databinding.ActivityMainBinding
import com.gukguk.habittracker.model.User
import com.gukguk.habittracker.util.SessionManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            val userDao = com.gukguk.habittracker.model.AppDatabase.getDatabase(this@MainActivity).userDao()
            if (userDao.findByUsername("test") == null) {
                userDao.register(User(username = "test", password = "123"))
            }
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val sessionManager = SessionManager(this)
        val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)

        navGraph.setStartDestination(
            if (sessionManager.isLoggedIn()) R.id.habitListFragment else R.id.loginFragment
        )
        navController.graph = navGraph

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}