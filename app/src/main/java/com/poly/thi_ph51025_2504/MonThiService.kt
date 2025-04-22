package com.poly.thi_ph51025_2504

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MonThiService {
    @GET("monthi")
    suspend fun getLit(): Response<List<MonThiResponse>>

    @GET("monthi/{id}")
    suspend fun getDetail(@Path("id") id: String): Response<MonThiResponse>
}

//b4