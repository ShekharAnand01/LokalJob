package com.shekhar.lokal.data.service

import com.shekhar.lokal.data.model.JobResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("common/jobs")
    suspend fun getJobs(@Query("page") page: Int): Response<JobResponse>
}