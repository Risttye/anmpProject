package com.example.myapplication.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.HabitListItemBinding
import com.example.myapplication.model.Habit

interface HabitCardListener {
    fun onIncreaseClick(habit: Habit)
    fun onDecreaseClick(habit: Habit)
    fun onTitleClick(habit: Habit)
}

class HabitAdapter(
    val habitList: ArrayList<Habit>,
    private val listener: HabitCardListener
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>(), HabitCardListener {

    class HabitViewHolder(var binding: HabitListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = HabitListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.binding.habit = habitList[position]
        holder.binding.listener = this
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }

    override fun onIncreaseClick(habit: Habit) {
        listener.onIncreaseClick(habit)
    }

    override fun onDecreaseClick(habit: Habit) {
        listener.onDecreaseClick(habit)
    }

    override fun onTitleClick(habit: Habit) {
        listener.onTitleClick(habit)
    }
}
