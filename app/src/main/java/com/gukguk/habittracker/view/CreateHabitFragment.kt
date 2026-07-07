package com.gukguk.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gukguk.habittracker.R
import com.gukguk.habittracker.databinding.FragmentCreateHabitBinding
import com.gukguk.habittracker.model.Habit
import com.gukguk.habittracker.util.FileHelper
import com.gukguk.habittracker.viewmodel.CreateHabitViewModel


class CreateHabitFragment : Fragment(R.layout.fragment_create_habit) {
    private lateinit var binding: FragmentCreateHabitBinding
    private lateinit var viewModel: CreateHabitViewModel
    private val ICONS = arrayOf("Fitness", "Water", "Read", "Meditation")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateHabitViewModel::class.java)

        val adapter:ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, ICONS)
        binding.txtIcon.setAdapter(adapter)

        binding.btnCreate.setOnClickListener {
            val name = binding.txtName.text.toString()
            val desc = binding.txtDesc.text.toString()
            val goal = binding.txtGoal.text.toString().toIntOrNull() ?: 0
            val unit = binding.txtUnit.text.toString()
            val icon = binding.txtIcon.text.toString()

            val fileHelper = FileHelper(requireContext())
            val json = fileHelper.readFromFile()

            val sType = object : TypeToken<List<Habit>>() {}.type
            val habitList = if (json.isNotEmpty()) {
                Gson().fromJson<ArrayList<Habit>>(json, sType)
            } else {
                arrayListOf()
            }

            val newHabit = Habit(
                0,
                name,
                desc,
                unit,
                goal,
                0,
                getImage(icon)
            )

            habitList.add(newHabit)
            viewModel.createHabit(newHabit)

            val action = CreateHabitFragmentDirections.actionReturnToHabitList()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel(view)
    }

    fun observeViewModel(view: View) {
        viewModel.habitCreatedLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val action = CreateHabitFragmentDirections.actionReturnToHabitList()
                Navigation.findNavController(view).navigate(action)
            }
        })
    }

    fun getImage(icon: String): Int {
        if (icon == "Fitness") {
            return R.drawable.fitness
        } else if (icon == "Water") {
            return R.drawable.water
        } else if (icon == "Read") {
            return R.drawable.read
        } else if (icon == "Meditation"){
            return R.drawable.meditation
        } else {
            return R.drawable.ic_launcher_foreground
        }
    }
}