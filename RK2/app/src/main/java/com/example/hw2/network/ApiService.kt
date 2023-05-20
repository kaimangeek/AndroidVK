package com.example.hw2.network

import com.example.hw2.model.Cat
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/cats?limit=20")
    fun loadCats(@Query("skip") page: Int) : Single<List<Cat>>
}