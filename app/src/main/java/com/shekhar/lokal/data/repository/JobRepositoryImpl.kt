package com.shekhar.lokal.data.repository

import com.shekhar.lokal.data.model.JobDetail
import com.shekhar.lokal.data.service.ApiService
import retrofit2.HttpException
import java.io.IOException

class JobRepositoryImpl(private val apiService: ApiService) : JobRepository {
    override suspend fun getJobs(page: Int): Result<List<JobDetail>> {
        return try {
            val response = apiService.getJobs(page)
            if (response.isSuccessful) {
                Result.success(response.body()?.results!!)
            } else {
                Result.failure(Exception(response.errorBody()!!.charStream().readText()))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure(Exception("Please Check Your Internet"))
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.failure(Exception("HTTP error occurred: ${e.code()}"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("An unexpected error occurred"))
        }
    }
}