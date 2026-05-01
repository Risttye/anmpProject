package com.example.myapplication.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.Habit
import android.view.LayoutInflater
import com.example.myapplication.databinding.HabitListItemBinding


class HabitAdapter(val habitList: ArrayList<Habit>) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {
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
}