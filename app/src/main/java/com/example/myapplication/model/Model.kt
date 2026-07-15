package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
data class Habit(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "current")
    var current: Int,
    @ColumnInfo(name = "goal")
    var goal: Int,
    @ColumnInfo(name = "unit")
    var unit: String,
    @ColumnInfo(name = "icon")
    var icon: String,
    @ColumnInfo(name = "status")
    var status: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "name")
    var name: String? = null
)
