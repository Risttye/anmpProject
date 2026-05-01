package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Habit

class HabitViewModel: ViewModel() {
    val habitsLD = MutableLiveData<ArrayList<Habit>>()

    fun refresh() {

    }
}