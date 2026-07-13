package com.gukguk.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.gukguk.habittracker.R
import com.gukguk.habittracker.databinding.FragmentEditHabitBinding
import com.gukguk.habittracker.model.Habit
import com.gukguk.habittracker.viewmodel.EditHabitViewModel

class EditHabitFragment : Fragment(R.layout.fragment_edit_habit) {
    private lateinit var binding: FragmentEditHabitBinding
    private lateinit var viewModel: EditHabitViewModel
    private var habitId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditHabitViewModel::class.java)

        habitId = EditHabitFragmentArgs.fromBundle(requireArguments()).habitId
        viewModel.loadHabit(habitId)

        binding.btnUpdate.setOnClickListener {
            val habit = binding.habit
            if (habit != null) {
                habit.name = binding.txtName.text.toString()
                habit.description = binding.txtDesc.text.toString()
                habit.goal = binding.txtGoal.text.toString().toIntOrNull() ?: habit.goal
                habit.unit = binding.txtUnit.text.toString()

                viewModel.updateHabit(habit)
            }
        }

        observeViewModel(view)
    }

    fun observeViewModel(view: View) {
        viewModel.habitLD.observe(viewLifecycleOwner, Observer {
            binding.habit = it
            binding.executePendingBindings()
        })
        viewModel.habitUpdatedLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val action = EditHabitFragmentDirections.actionReturnToHabitList()
                Navigation.findNavController(view).navigate(action)
            }
        })
    }
}