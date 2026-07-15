package com.example.myapplication.util

import android.content.Context
import com.example.myapplication.model.HabitDatabase

fun buildDb(context: Context): HabitDatabase {
    return HabitDatabase.getInstance(context)
}
