package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Habit
import org.json.JSONArray
import org.json.JSONObject

class HabitViewModel: ViewModel() {
    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    private var initialized = false
    private var appContext: Context? = null

    companion object {
        private const val PREF_NAME = "habit_preferences"
        private const val KEY_HABITS = "habits"

        private val habits = arrayListOf<Habit>()
        private val defaultHabits = arrayListOf(
            Habit("Drink Water", "Stay hydrated throughout the day", 3, 8, "glasses", "Water", "In Progress"),
            Habit("Exercise", "Daily workout routine", 15, 30, "minutes", "Fitness", "In Progress"),
            Habit("Read Books", "Expand your knowledge", 20, 20, "pages", "Book", "Completed"),
            Habit("Meditation", "Mindfulness practice", 0, 10, "minutes", "Meditation", "In Progress")
        )
    }

    fun initialize(context: Context) {
        appContext = context.applicationContext
        if (!initialized) {
            loadHabits()
            initialized = true
        }
        refresh()
    }

    fun refresh() {
        habitsLD.value = ArrayList(habits)
    }

    fun addHabit(habit: Habit) {
        habits.add(habit)
        saveHabits()
        refresh()
    }

    fun updateProgress(position: Int, step: Int) {
        val habit = habits.getOrNull(position) ?: return
        habit.current = (habit.current + step).coerceIn(0, habit.goal)
        habit.status = if (habit.current >= habit.goal) "Completed" else "In Progress"
        saveHabits()
        refresh()
    }

    private fun loadHabits() {
        val savedHabits = appContext
            ?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            ?.getString(KEY_HABITS, null)

        habits.clear()
        if (savedHabits.isNullOrBlank()) {
            habits.addAll(defaultHabits)
            saveHabits()
            return
        }

        try {
            val jsonArray = JSONArray(savedHabits)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                habits.add(
                    Habit(
                        jsonObject.optString("name"),
                        jsonObject.optString("description"),
                        jsonObject.optInt("current"),
                        jsonObject.optInt("goal"),
                        jsonObject.optString("unit"),
                        jsonObject.optString("icon"),
                        jsonObject.optString("status")
                    )
                )
            }
        } catch (_: Exception) {
            habits.addAll(defaultHabits)
            saveHabits()
        }
    }

    private fun saveHabits() {
        val jsonArray = JSONArray()
        habits.forEach { habit ->
            jsonArray.put(
                JSONObject().apply {
                    put("name", habit.name)
                    put("description", habit.description)
                    put("current", habit.current)
                    put("goal", habit.goal)
                    put("unit", habit.unit)
                    put("icon", habit.icon)
                    put("status", habit.status)
                }
            )
        }

        appContext
            ?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            ?.edit()
            ?.putString(KEY_HABITS, jsonArray.toString())
            ?.apply()
    }
}
