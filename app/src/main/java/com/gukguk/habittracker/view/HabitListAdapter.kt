package com.gukguk.habittracker.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gukguk.habittracker.databinding.CardHabitBinding
import com.gukguk.habittracker.model.Habit

interface HabitCardListener {
    fun onAdd(habit: Habit)
    fun onSub(habit: Habit)
    fun onTitleClick(habit: Habit)

    fun onEditClick(habit: Habit)
}

class HabitListAdapter (val habitList:ArrayList<Habit>, val context: Context, val listener: HabitCardListener)
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

        holder.binding.imgHabit.setImageResource(habitList[position].imageID)
        holder.binding.progressBar.max = habitList[position].goal
        holder.binding.progressBar.progress = habitList[position].progress

        val habit = habitList[position]

        holder.binding.habit = habit
        holder.binding.listener = listener
        holder.binding.executePendingBindings()
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
}