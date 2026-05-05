package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Habit

class HabitViewModel: ViewModel() {
    val habitsLD = MutableLiveData<ArrayList<Habit>>()

    companion object {
        private val habits = arrayListOf(
            Habit("Drink Water", "Stay hydrated throughout the day", 3, 8, "glasses", "Water", "In Progress"),
            Habit("Exercise", "Daily workout routine", 15, 30, "minutes", "Fitness", "In Progress"),
            Habit("Read Books", "Expand your knowledge", 20, 20, "pages", "Book", "Completed"),
            Habit("Meditation", "Mindfulness practice", 0, 10, "minutes", "Meditation", "In Progress")
        )
    }

    fun refresh() {
        habitsLD.value = ArrayList(habits)
    }

    fun addHabit(habit: Habit) {
        habits.add(habit)
        refresh()
    }

    fun updateProgress(position: Int, step: Int) {
        val habit = habits.getOrNull(position) ?: return
        habit.current = (habit.current + step).coerceIn(0, habit.goal)
        habit.status = if (habit.current >= habit.goal) "Completed" else "In Progress"
        refresh()
    }
}
