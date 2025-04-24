package com.poly.thi_ph51025

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class RetrofitService() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val monThiService: MonThiService by lazy {
        retrofit.create(MonThiService::class.java)
    }
}
//b4