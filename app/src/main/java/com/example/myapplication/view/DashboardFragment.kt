package com.example.myapplication.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.example.myapplication.viewmodel.HabitViewModel

class DashboardFragment : Fragment() {


    private lateinit var viewModel: HabitViewModel
    private lateinit var habitAdapter: HabitAdapter
    private lateinit var binding: FragmentDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)
        viewModel.initialize(requireContext())
        habitAdapter = HabitAdapter(arrayListOf()) { position, step ->
            if (position != RecyclerView.NO_POSITION) {
                viewModel.updateProgress(position, step)
            }
        }

        binding.recHabit.layoutManager = LinearLayoutManager(context)
        binding.recHabit.adapter = habitAdapter
        binding.fabAddHabit.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.actionDashboardToCreateHabit)
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.habitsLD.observe(viewLifecycleOwner, Observer {
            habitAdapter.updateHabitList(it)
            val completed = it.count { habit ->
                habit.goal > 0 && habit.current >= habit.goal || habit.status.equals("Completed", true)
            }
            binding.txtSummary.text = if (it.isEmpty()) {
                "Tap + to add your first habit"
            } else {
                "$completed of ${it.size} habits completed"
            }
            val allDone = completed == it.size && it.isNotEmpty()
            binding.txtTopStatus.text = if (allDone) {
                "All Done"
            } else if (it.isEmpty()) {
                "No Habits"
            } else {
                "In Progress"
            }
            binding.txtTopStatus.setTextColor(
                if (allDone) Color.parseColor("#1B8A5A") else Color.parseColor("#7A4B00")
            )
            binding.txtTopStatus.backgroundTintList = ColorStateList.valueOf(
                if (allDone) Color.parseColor("#DDF7E9") else Color.parseColor("#FFF1D6")
            )
            binding.emptyState.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.recHabit.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
        })
    }

}
