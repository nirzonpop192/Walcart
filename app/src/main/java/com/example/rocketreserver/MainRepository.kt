package com.example.rocketreserver

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException

class MainRepository constructor(private val apolloClient: ApolloClient) {

    suspend fun getAllMovies() : List<GetCategoriesListQuery.Category>? {
//        val response = retrofitService.getAllMovies()

        val response = try {apolloClient.query(GetCategoriesListQuery()).execute()
        } catch (e: ApolloException) {
            Log.d("LaunchList", "Failure", e)
            null
        }
        val launches = response?.data?.getCategories?.result?.categories?.filterNotNull()
        return launches
    }

}