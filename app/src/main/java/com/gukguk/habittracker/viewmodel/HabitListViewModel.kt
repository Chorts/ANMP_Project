package com.gukguk.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gukguk.habittracker.model.AppDatabase
import com.gukguk.habittracker.model.Habit


class HabitListViewModel(application: Application): AndroidViewModel(application)  {
    val habitLD = MutableLiveData<ArrayList<Habit>>()

    val habitLoadErrorLD = MutableLiveData<Boolean>()

    val loadingLD = MutableLiveData<Boolean>()

    private val habitDao = AppDatabase.getDatabase(application).habitDao()

    fun loadHabit() {
        loadingLD.value = true
        habitLoadErrorLD.value = false

        habitDao.getAll().observeForever {
            habitLD.value = ArrayList(it)
            loadingLD.value = false
        }
    }

    fun onAdd(habit: Habit) {
        if (habit.progress < habit.goal) {
            habit.progress++
            Thread { habitDao.update(habit) }.start()
        }
    }

    fun onSub(habit: Habit) {
        if (habit.progress > 0) {
            habit.progress--
            Thread {
                habitDao.update(habit)
            }.start()
        }
    }
}