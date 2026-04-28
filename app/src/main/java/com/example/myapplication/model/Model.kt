package com.example.myapplication.model

data class Habit(
    val name: String,
    val description: String,
    val progress: Int,
    val progressText: String,
    val status : String
)