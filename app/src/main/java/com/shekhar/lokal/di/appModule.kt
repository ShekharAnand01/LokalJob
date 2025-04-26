package com.shekhar.lokal.di

import androidx.room.Room
import com.shekhar.lokal.data.db.AppDatabase
import com.shekhar.lokal.data.repository.JobRepository
import com.shekhar.lokal.data.repository.JobRepositoryImpl
import com.shekhar.lokal.data.service.ApiService
import com.shekhar.lokal.viewmodel.JobViewModel
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(12, TimeUnit.SECONDS)
            .readTimeout(6, TimeUnit.SECONDS)
            .writeTimeout(8, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://testapi.getlokalapp.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app-db")
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single { get<AppDatabase>().jobDetailDao() }
    single { get<Retrofit>().create(ApiService::class.java) }
    single { get<AppDatabase>().jobDetailDao() }

    single<JobRepository> { JobRepositoryImpl(get()) }

    viewModel { JobViewModel(get(), get()) }
}