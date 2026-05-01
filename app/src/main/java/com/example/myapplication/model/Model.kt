package com.example.myapplication.model

data class Habit(
    val name: String,
    val description: String,
    //val progress: Int,
    //val progressText: String,
    //val status : String,
    var currentProgress: Int,
    val goal: Int,
    val unit: String, //satuan
    val icon: String, //kategori
    var status : String = "In Progress"
)

object HabitStorage {
    val habits = arrayListOf<Habit>()

    // Fungsi init ini opsional, hanya agar saat login pertama kali tidak kosong
    init {
        habits.add(Habit("Drink Water", "Stay hydrated", 2, 8, "glasses", "Water", "In Progress"))
        habits.add(Habit("Exercise", "Daily workout", 15, 30, "minutes", "Fitness", "In Progress"))
    }}