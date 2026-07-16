package com.example.myapplication.view

import androidx.databinding.ObservableField
import com.example.myapplication.model.Habit

class EditHabitForm(habit: Habit) {
    val name = ObservableField(habit.name)
    val description = ObservableField(habit.description)
    val goal = ObservableField(habit.goal.toString())
    val unit = ObservableField(habit.unit)
    val icon = ObservableField(habit.icon)
}
