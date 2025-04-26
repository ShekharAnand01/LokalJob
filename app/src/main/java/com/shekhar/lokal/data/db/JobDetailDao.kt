package com.shekhar.lokal.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobDetailEntity)

    @Query("SELECT * FROM job_details")
    fun getAllJobDetails(): Flow<List<JobDetailEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM job_details WHERE id = :id)")
    fun isJobExists(id: Int): Flow<Boolean>

    @Query("DELETE FROM job_details WHERE id = :id")
    suspend fun clearJob(id: Int)
}
