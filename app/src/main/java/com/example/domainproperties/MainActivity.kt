package com.example.domainproperties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domainproperties.databinding.ActivityMainBinding
import com.example.domainproperties.network.ApiService
import com.example.domainproperties.repository.PropertiesRepository
import com.example.domainproperties.ui.RecyclerViewAdapter
import com.example.domainproperties.viewmodel.MainViewModel
import com.example.domainproperties.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val apiService = ApiService.instance
    private val propertiesRepository = PropertiesRepository(apiService)
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(propertiesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fetch properties to buy
        viewModel.fetchProperties(getString(R.string.buy))

        //observe livedata
        viewModel.propsImmutableState.observe(this) { buyList ->
            if (buyList.isNotEmpty()) {//display properties
                binding.recyclerview.visibility = View.VISIBLE
                binding.recyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = RecyclerViewAdapter(buyList)
                }
            } else {//no properties to display
                binding.recyclerview.visibility = View.GONE
            }
            binding.progressBar.visibility = View.GONE
        }

        viewModel.errorImmutableState.observe(this) {
            binding.textview.apply {
                text = it
                visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()

        //set toggle button
        binding.switchProperty.setOnCheckedChangeListener { _, isChecked ->

            //reset view
            binding.progressBar.visibility = View.VISIBLE
            binding.recyclerview.visibility = View.GONE

            //fetch properties to buy or rent
            viewModel.fetchProperties(if (isChecked) getString(R.string.rent) else getString(R.string.buy))
        }
    }
}