package com.example.myapplication.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Habit
import android.view.LayoutInflater
import com.example.myapplication.databinding.HabitListItemBinding


class HabitAdapter(
    val habitList: ArrayList<Habit>,
    private val onProgressChanged: (Int, Int) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitAdapter.HabitViewHolder {
        val binding = HabitListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitAdapter.HabitViewHolder, position: Int) {
        val habit = habitList[position]
        holder.binding.txtNameHabit.text = habit.name
        holder.binding.txtDescription.text = habit.description
        holder.binding.txtProgressValue.text = "${habit.current} / ${habit.goal} ${habit.unit}"

        val progress = if (habit.goal > 0) {
            (habit.current * 100 / habit.goal).coerceIn(0, 100)
        } else {
            0
        }
        val isCompleted = progress >= 100 || habit.status.equals("Completed", true)
        val statusText = if (isCompleted) "Completed" else habit.status
        val statusColor = if (isCompleted) Color.parseColor("#1B8A5A") else Color.parseColor("#A46200")
        val progressColor = if (isCompleted) Color.parseColor("#28A96B") else Color.parseColor("#F0A33A")

        holder.binding.txtStatus.text = statusText
        holder.binding.txtStatus.setTextColor(statusColor)
        holder.binding.txtStatus.backgroundTintList = ColorStateList.valueOf(
            if (isCompleted) Color.parseColor("#DDF7E9") else Color.parseColor("#FFF1D6")
        )
        holder.binding.progressBar.progress = progress
        holder.binding.progressBar.progressTintList = ColorStateList.valueOf(progressColor)
        holder.binding.progressBar.progressBackgroundTintList = ColorStateList.valueOf(Color.parseColor("#E6EAF0"))
        holder.binding.imageIcon.setImageResource(getIconRes(habit.icon))
        holder.binding.btnDecrease.isEnabled = habit.current > 0
        holder.binding.btnIncrease.isEnabled = habit.current < habit.goal
        holder.binding.btnDecrease.alpha = if (habit.current > 0) 1f else 0.45f
        holder.binding.btnIncrease.alpha = if (habit.current < habit.goal) 1f else 0.45f
        holder.binding.btnDecrease.setOnClickListener {
            onProgressChanged(holder.bindingAdapterPosition, -1)
        }
        holder.binding.btnIncrease.setOnClickListener {
            onProgressChanged(holder.bindingAdapterPosition, 1)
        }

    }
    override fun getItemCount(): Int {
        return habitList.size
    }

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }

    class HabitViewHolder(var binding: HabitListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    private fun getIconRes(icon: String): Int {
        return when (icon.lowercase()) {
            "water" -> R.drawable.ic_habit_water
            "fitness" -> R.drawable.ic_habit_fitness
            "book" -> R.drawable.ic_habit_book
            "meditation" -> R.drawable.ic_habit_meditation
            else -> R.drawable.ic_habit_water
        }
    }
}
