package com.example.drilldownpattern

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drilldownpattern.models.*

@Database(
    entities = [Country::class, Zone::class, Region::class, Area::class, Employee::class],
    version = 1,
    exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {

    companion object {
        @Volatile
        var INSTANCE: MyDatabase? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context, MyDatabase::class.java, "local_db")
                .build()
                .also {
                    INSTANCE = it
                }
        }
    }

    abstract fun getDao(): Dao

}