package com.shekhar.lokal.data.repository

import com.shekhar.lokal.data.model.JobDetail




interface JobRepository {
    suspend fun getJobs(page: Int): Result<List<JobDetail>>

}