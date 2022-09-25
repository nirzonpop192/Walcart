package com.example.rocketreserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.example.rocketreserver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // view binding for the activity
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        Log.e("dim", "on Create ")

    }

    override fun onResume() {
        super.onResume()
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://devapiv2.walcart.com/graphql")
            .build()

        //  apolloClient.query(GetCategoriesListQuery()).execute()
        Log.e("dim", "on resume ")
        lifecycleScope.launchWhenResumed {
            val response = try {apolloClient.query(GetCategoriesListQuery()).execute()
            } catch (e: ApolloException) {
                Log.d("LaunchList", "Failure", e)
                null
            }
            val launches = response?.data?.getCategories?.result?.categories?.filterNotNull()
            if (launches != null && !response.hasErrors()) {
                val adapter = CategoriesListAdapter(this@MainActivity,launches,
                    object:CategoriesListAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            binding.tvResult.text=response?.data?.getCategories?.result?.categories?.get(position)?.parents?.size.toString()

                            val SubAdapter = SubCategoriesListAdapter(this@MainActivity,response?.data?.getCategories?.result?.categories?.get(position)?.parents)
                            binding.rvSubCategories.layoutManager = LinearLayoutManager(this@MainActivity)
                            binding.rvSubCategories.adapter = SubAdapter

                        }
                    })
                binding.rvCategories.layoutManager = LinearLayoutManager(this@MainActivity)
                binding.rvCategories.adapter = adapter
            }
           // Log.e("dim", "Success ${response.data}") SubCategoriesListAdapter
        }
    }
}
