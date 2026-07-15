package com.example.myapplication.util

import android.content.Context
import androidx.room.Room
import com.example.myapplication.database.HabitDatabase

object Util {

    private var INSTANCE: HabitDatabase? = null

    fun getDatabase(context: Context): HabitDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                HabitDatabase::class.java,
                "habit_database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()

            INSTANCE = instance
            instance
        }
    }
}