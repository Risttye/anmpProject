package com.example.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCreateHabitBinding
import com.example.myapplication.model.Habit
import com.example.myapplication.viewmodel.HabitViewModel


class CreateHabitFragment : Fragment() {

    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var viewModel: HabitViewModel

    //override fun onCreate(savedInstanceState: Bundle?) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)
        viewModel.initialize(requireContext())

        val icons = arrayOf("Water", "Fitness", "Book", "Meditation")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, icons)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIcon.adapter = adapter
        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.btnCreateHabit.setOnClickListener {
            val name = binding.txtHabitName.text.toString()
            val desc = binding.txtShortDescription.text.toString()
            val goal = binding.txtGoal.text.toString().toIntOrNull() ?: 0
            val unit = binding.txtUnit.text.toString()
            val icon = binding.spinnerIcon.selectedItem.toString()

            if (name.isNotEmpty() && desc.isNotEmpty() && goal > 0 && unit.isNotEmpty()) {

                val newHabit = Habit(name, desc, 0, goal, unit, icon, "In Progress")

                // Kirim ke ViewModel untuk disimpan di Gudang (HabitStorage)
                viewModel.addHabit(newHabit)

                // Kembali ke Dashboard[cite: 1]
                Navigation.findNavController(it).popBackStack()
            } else {
                // Tampilkan pesan jika ada yang kosong (optional)
            }
        }
    }
}
