package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myapplication.databinding.FragmentEditHabitBinding
import com.example.myapplication.model.Habit
import com.example.myapplication.viewmodel.HabitViewModel

class EditHabitFragment : Fragment() {

    private lateinit var binding: FragmentEditHabitBinding
    private lateinit var viewModel: HabitViewModel
    private var habit: Habit? = null
    private val icons = arrayOf("Water", "Fitness", "Book", "Meditation")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[HabitViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.spinnerIcon.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            icons
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.btnSubmit.setOnClickListener { submitHabit() }

        viewModel.habitLD.observe(viewLifecycleOwner) { loadedHabit ->
            if (loadedHabit != null) {
                habit = loadedHabit
                binding.form = EditHabitForm(loadedHabit)
                val selectedIcon = icons.indexOfFirst { it.equals(loadedHabit.icon, true) }
                binding.spinnerIcon.setSelection(selectedIcon.coerceAtLeast(0))
                binding.progressLoading.visibility = View.GONE
                binding.editContent.visibility = View.VISIBLE
            }
        }

        viewModel.habitSavedLD.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                viewModel.consumeHabitSaved()
                Toast.makeText(requireContext(), "Habit updated", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).popBackStack()
            }
        }

        val habitId = arguments?.getInt("habitId", 0) ?: 0
        if (habitId == 0) {
            Toast.makeText(requireContext(), "Habit not found", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view).popBackStack()
        } else {
            viewModel.fetch(habitId)
        }
    }

    private fun submitHabit() {
        val form = binding.form ?: return
        val name = form.name.get().orEmpty().trim()
        val description = form.description.get().orEmpty().trim()
        val goal = form.goal.get().orEmpty().toIntOrNull() ?: 0
        val unit = form.unit.get().orEmpty().trim()

        binding.txtHabitName.error = if (name.isEmpty()) "Habit name is required" else null
        binding.txtShortDescription.error = if (description.isEmpty()) "Description is required" else null
        binding.txtGoal.error = if (goal <= 0) "Goal must be greater than 0" else null
        binding.txtUnit.error = if (unit.isEmpty()) "Unit is required" else null
        if (name.isEmpty() || description.isEmpty() || goal <= 0 || unit.isEmpty()) return

        val updatedHabit = habit ?: return
        updatedHabit.name = name
        updatedHabit.description = description
        updatedHabit.goal = goal
        updatedHabit.unit = unit
        updatedHabit.icon = binding.spinnerIcon.selectedItem.toString()
        updatedHabit.current = updatedHabit.current.coerceAtMost(goal)
        updatedHabit.status = if (updatedHabit.current >= goal) "Completed" else "In Progress"

        binding.btnSubmit.isEnabled = false
        viewModel.updateHabitDetails(updatedHabit)
    }
}
