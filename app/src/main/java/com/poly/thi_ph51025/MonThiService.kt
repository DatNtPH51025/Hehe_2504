package com.poly.thi_ph51025

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MonThiService {
    @GET("MonThi")
    suspend fun getLit(): Response<List<MonThiResponse>>

    @GET("MonThi/{id}")
    suspend fun getDetail(@Path("id") id: String): Response<MonThiResponse>
}
//b3