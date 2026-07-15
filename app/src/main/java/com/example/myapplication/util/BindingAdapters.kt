package com.example.myapplication.util

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.myapplication.R
import com.example.myapplication.model.Habit

@BindingAdapter("habitIcon")
fun setHabitIcon(view: ImageView, icon: String?) {
    val resourceId = when (icon?.lowercase()) {
        "water" -> R.drawable.ic_habit_water
        "fitness" -> R.drawable.ic_habit_fitness
        "book" -> R.drawable.ic_habit_book
        "meditation" -> R.drawable.ic_habit_meditation
        else -> R.drawable.ic_habit_water
    }
    view.setImageResource(resourceId)
}

@BindingAdapter("habitStatusStyle")
fun setHabitStatusStyle(view: TextView, habit: Habit?) {
    if (habit == null) return
    val status = habit.status
    view.text = status
    if (status.equals("Completed", ignoreCase = true)) {
        view.setTextColor(Color.parseColor("#1B8A5A"))
        view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#DDF7E9"))
    } else {
        view.setTextColor(Color.parseColor("#7A4B00"))
        view.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFF1D6"))
    }
}

@BindingAdapter("habitProgressStyle")
fun setHabitProgressStyle(view: ProgressBar, habit: Habit?) {
    if (habit == null) return
    view.progress = habit.current
}

@BindingAdapter("btnDecreaseStyle")
fun setBtnDecreaseStyle(view: ImageButton, habit: Habit?) {
    if (habit == null) return
    val enabled = habit.current > 0
    view.isEnabled = enabled
    view.alpha = if (enabled) 1.0f else 0.5f
}

@BindingAdapter("btnIncreaseStyle")
fun setBtnIncreaseStyle(view: ImageButton, habit: Habit?) {
    if (habit == null) return
    val enabled = habit.current < habit.goal
    view.isEnabled = enabled
    view.alpha = if (enabled) 1.0f else 0.5f
}
