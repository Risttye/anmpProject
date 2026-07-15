package com.example.myapplication.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.database.HabitDao
import com.example.myapplication.model.Habit
import com.example.myapplication.util.Util
import org.json.JSONArray
import org.json.JSONObject

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    val habitsLD = MutableLiveData<ArrayList<Habit>>()

    private val habitDao: HabitDao

    init {
        val db = Util.getDatabase(application)
        habitDao = db.habitDao()
        refresh()
    }

    fun refresh() {
        habitsLD.value = ArrayList(habitDao.getAllHabit())
    }

    fun addHabit(habit: Habit) {
        habitDao.insertHabit(habit)
        refresh()
    }

    fun updateProgress(position: Int, step: Int) {

        val list = habitsLD.value ?: return

        if (position !in list.indices) return

        val habit = list[position]

        habit.current = (habit.current + step).coerceIn(0, habit.goal)

        habit.status =
            if (habit.current >= habit.goal)
                "Completed"
            else
                "In Progress"

        habitDao.updateHabit(habit)

        refresh()
    }

    fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
        refresh()
    }
}