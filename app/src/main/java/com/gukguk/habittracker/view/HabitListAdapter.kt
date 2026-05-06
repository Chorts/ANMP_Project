package com.gukguk.habittracker.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.gukguk.habittracker.databinding.CardHabitBinding
import com.gukguk.habittracker.model.Habit
import com.gukguk.habittracker.util.FileHelper

class HabitListAdapter (val habitList:ArrayList<Habit>, val context: Context)
    : RecyclerView.Adapter<HabitListAdapter.HabitViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitViewHolder {
        val binding = CardHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HabitViewHolder,
        position: Int
    ) {
        if (habitList[position].progress == habitList[position].goal) {
            holder.binding.txtStatus.text = "Completed"
            holder.binding.btnAdd.isEnabled = false
        } else {
            holder.binding.txtStatus.text = "In Progress"
            holder.binding.btnAdd.isEnabled = true
        }

        holder.binding.txtName.text = habitList[position].name
        holder.binding.txtDescription.text = habitList[position].description
        holder.binding.txtProgress.text = "${habitList[position].progress} / ${habitList[position].goal} ${habitList[position].unit}"
        holder.binding.imgHabit.setImageResource(habitList[position].imageID)
        holder.binding.progressBar.max = habitList[position].goal
        holder.binding.progressBar.progress = habitList[position].progress

        val habit = habitList[position]

        holder.binding.btnAdd.setOnClickListener {
            if (habit.progress < habit.goal) {
                habit.progress++
                notifyItemChanged(position)
                saveToFile()
            }
        }

        holder.binding.btnSub.setOnClickListener {
            if (habit.progress > 0) {
                habit.progress--
                notifyItemChanged(position)
                saveToFile()
            }
        }
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    class HabitViewHolder(var binding: CardHabitBinding)
        :RecyclerView.ViewHolder(binding.root)

    fun updateHabitList(newHabitList: ArrayList<Habit>) {
        habitList.clear()
        habitList.addAll(newHabitList)
        notifyDataSetChanged()
    }

    fun saveToFile() {
        val fileHelper = FileHelper(context)
        val json = Gson().toJson(habitList)
        fileHelper.writeToFile(json)
    }
}