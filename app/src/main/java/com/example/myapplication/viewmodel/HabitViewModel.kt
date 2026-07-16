package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.model.Habit
import com.example.myapplication.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HabitViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val habitsLD = MutableLiveData<ArrayList<Habit>>()
    val habitLD = MutableLiveData<Habit?>()
    val habitSavedLD = MutableLiveData(false)
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        launch {
            val db = buildDb(getApplication())
            val habitList = db.habitDao().selectAllHabits()
            habitsLD.postValue(ArrayList(habitList))
        }
    }

    fun addHabit(habit: Habit) {
        habitSavedLD.value = false
        launch {
            val db = buildDb(getApplication())
            db.habitDao().insert(habit)
            habitSavedLD.postValue(true)
        }
    }

    fun updateProgress(habit: Habit, step: Int) {
        launch {
            val db = buildDb(getApplication())
            habit.current = (habit.current + step).coerceIn(0, habit.goal)
            habit.status = if (habit.current >= habit.goal) "Completed" else "In Progress"
            db.habitDao().updateHabit(habit)
            refresh()
        }
    }

    fun updateHabitDetails(habit: Habit) {
        habitSavedLD.value = false
        launch {
            val db = buildDb(getApplication())
            db.habitDao().updateHabit(habit)
            habitSavedLD.postValue(true)
        }
    }

    fun fetch(id: Int) {
        habitLD.value = null
        launch {
            val db = buildDb(getApplication())
            habitLD.postValue(db.habitDao().selectHabit(id))
        }
    }

    fun consumeHabitSaved() {
        habitSavedLD.value = false
    }

    fun deleteHabit(habit: Habit) {
        launch {
            val db = buildDb(getApplication())
            db.habitDao().deleteHabit(habit)
            refresh()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
