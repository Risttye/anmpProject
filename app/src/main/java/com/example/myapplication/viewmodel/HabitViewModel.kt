package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Habit
import com.example.myapplication.model.HabitStorage

class HabitViewModel: ViewModel() {
    val habitsLD = MutableLiveData<ArrayList<Habit>>()

    fun refresh() {
        habitsLD.value = HabitStorage.habits
//        val habits = arrayListOf(
//            Habit(
//                "Drink Water",
//                "Stay hydrated throughout the day",
//                40,
//                "3 / 8 glasses",
//                "In Progress"
//            ),
//            Habit(
//                "Exercise",
//                "Daily workout routine",
//                50,
//                "15 / 30 minutes",
//                "In Progress"
//            ),
//            Habit(
//                "Read Books",
//                "Expand your knowledge",
//                100,
//                "20 / 20 pages",
//                "Completed"
//            ),
//            Habit(
//                "Meditation",
//                "Mindfulness practice",
//                0,
//                "0 / 10 minutes",
//                "In Progress"
//            )
//        )
//
//        habitsLD.value = habits
    }

    fun addHabit(newHabit: Habit) {
        HabitStorage.habits.add(newHabit)
        refresh()
    }
}