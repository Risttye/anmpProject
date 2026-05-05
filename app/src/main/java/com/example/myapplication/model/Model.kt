package com.example.myapplication.model

data class Habit(
    val name: String,
    val description: String,
    var current: Int,
    val goal: Int,
    val unit: String,
    val icon: String,
    var status: String,
)
