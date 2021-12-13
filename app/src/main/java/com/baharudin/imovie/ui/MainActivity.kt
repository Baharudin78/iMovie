package com.baharudin.imovie.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.baharudin.imovie.R
import com.baharudin.imovie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

private lateinit var binding : ActivityMainBinding
private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigation = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navigation.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.detailFragment,
                R.id.seeAllFragment ->
                    binding.bottomNavigationView.visibility = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
        navController = navigation.findNavController()
        binding.apply {
            bottomNavigationView.setupWithNavController(navController)
        }
    }
}