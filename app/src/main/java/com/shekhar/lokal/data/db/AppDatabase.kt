package com.shekhar.lokal.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shekhar.lokal.navigation.Converters

@Database(entities = [JobDetailEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jobDetailDao(): JobDetailDao
}
