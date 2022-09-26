package com.example.rocketreserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.example.rocketreserver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // view binding for the activity
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        Log.e("dim", "on Create ")
        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://devapiv2.walcart.com/graphql")
            .build()

        val mainRepository = MainRepository(apolloClient)
        //binding.recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(MainViewModel::class.java)


        viewModel.movieList.observe(this) {
            //adapter.setMovies(it)

            val adapter = it?.let { it1 ->
                CategoriesListAdapter(this@MainActivity, it1,
                    object:CategoriesListAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            //  Log.e("test",response.data?.getCategories?.result?.categories?.get(position)?.parents?.size.toString())

                            val SubAdapter = SubCategoriesListAdapter(this@MainActivity,it1.get(position)?.parents)
                            binding.rvSubCategories.layoutManager = LinearLayoutManager(this@MainActivity)
                            binding.rvSubCategories.adapter = SubAdapter

                        }
                    })
            }
            binding.rvCategories.layoutManager = LinearLayoutManager(this@MainActivity)
            binding.rvCategories.adapter = adapter
        }

//        viewModel.errorMessage.observe(this) {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//        }

//        viewModel.loading.observe(this, Observer {
//            if (it) {
//                binding.progressDialog.visibility = View.VISIBLE
//            } else {
//                binding.progressDialog.visibility = View.GONE
//            }
//        })

        viewModel.getAllMovies()



    }

//    override fun onResume() {
//        super.onResume()
//        val apolloClient = ApolloClient.Builder()
//            .serverUrl("https://devapiv2.walcart.com/graphql")
//            .build()
//
//        //  apolloClient.query(GetCategoriesListQuery()).execute()
//        Log.e("dim", "on resume ")
//        lifecycleScope.launchWhenResumed {
//            val response = try {apolloClient.query(GetCategoriesListQuery()).execute()
//            } catch (e: ApolloException) {
//                Log.d("LaunchList", "Failure", e)
//                null
//            }
//            val launches = response?.data?.getCategories?.result?.categories?.filterNotNull()
//            if (launches != null && !response.hasErrors()) {
//                val adapter = CategoriesListAdapter(this@MainActivity,launches,
//                    object:CategoriesListAdapter.OnItemClickListener{
//                        override fun onItemClick(position: Int) {
//                          Log.e("test",response.data?.getCategories?.result?.categories?.get(position)?.parents?.size.toString())
//
//                            val SubAdapter = SubCategoriesListAdapter(this@MainActivity,response?.data?.getCategories?.result?.categories?.get(position)?.parents)
//                            binding.rvSubCategories.layoutManager = LinearLayoutManager(this@MainActivity)
//                            binding.rvSubCategories.adapter = SubAdapter
//
//                        }
//                    })
//                binding.rvCategories.layoutManager = LinearLayoutManager(this@MainActivity)
//                binding.rvCategories.adapter = adapter
//            }
//           // Log.e("dim", "Success ${response.data}") SubCategoriesListAdapter
//        }
//    }
}
