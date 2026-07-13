package com.gukguk.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gukguk.habittracker.model.AppDatabase
import com.gukguk.habittracker.model.Habit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EditHabitViewModel(application: Application) : AndroidViewModel(application),
    CoroutineScope {
    private var job = Job()
    private val habitDao = AppDatabase.getDatabase(application).habitDao()
    val habitLD = MutableLiveData<Habit>()
    val habitUpdatedLD = MutableLiveData<Boolean>()

    fun loadHabit(id: Int) {
        launch {
            val habit = habitDao.getById(id)
            habitLD.postValue(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        launch {
            habitDao.update(habit)
            habitUpdatedLD.postValue(true)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}
