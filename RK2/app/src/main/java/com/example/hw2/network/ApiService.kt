package com.example.hw2.network

import com.example.hw2.model.Cat
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {

    @GET("api/cats?limit=20&skip=0")
    fun loadMovies() : Single<List<Cat>>
}