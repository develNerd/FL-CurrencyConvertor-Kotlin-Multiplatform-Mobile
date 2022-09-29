package org.flepper.currencyconvertor.android

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.navigation.fragment.NavHostFragment
import org.flepper.currencyconvertor.android.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainActivityViewModel: MainActivityViewModel by viewModels()

        mainActivityViewModel.getCurrencyRates()
        val navHostFragment  = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        if(savedInstanceState == null){
            navController.navigate(R.id.homeFragment)
        }

    }
}

