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
        val habit = habitList[position]
        holder.binding.txtNameHabit.text = habit.name
        holder.binding.txtDescription.text = habit.description

        //(sekarang / target) * 100
        val progressPercentage = (habit.currentProgress.toFloat() / habit.goal.toFloat() * 100).toInt()
        holder.binding.progressBar.progress = progressPercentage

        holder.binding.txtTarget.text = "${habit.currentProgress} / ${habit.goal} ${habit.unit}"

        holder.binding.txtStatus.text = habit.status

        if (habit.currentProgress >= habit.goal) {
            habit.status = "Completed"
        } else {
            habit.status = "In Progress"
        }

        holder.binding.btnPlus.setOnClickListener {
            if (habit.currentProgress < habit.goal) {
                habit.currentProgress++ // Tambah progress data

                // Cek jika sudah mencapai target
                if (habit.currentProgress >= habit.goal) {
                    habit.status = "Completed"
                }

                // Memperbarui tampilan kartu ini saja
                notifyItemChanged(position)
            }
        }

        holder.binding.btnMinus.setOnClickListener {
            if (habit.currentProgress > 0) {
                habit.currentProgress-- // Kurangi progress data

                // Jika dikurangi dari target, status kembali ke In Progress
                if (habit.currentProgress < habit.goal) {
                    habit.status = "In Progress"
                }

                notifyItemChanged(position)
            }
        }

//        holder.binding.progressBar.progress = habitList[position].progress ?: 0
//        holder.binding.txtTarget.text = habitList[position].progressText
//        holder.binding.txtStatus.text = habitList[position].status

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