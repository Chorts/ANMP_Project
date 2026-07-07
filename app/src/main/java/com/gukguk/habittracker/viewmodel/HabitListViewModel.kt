package com.gukguk.habittracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.gukguk.habittracker.model.AppDatabase
import com.gukguk.habittracker.model.Habit
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class HabitListViewModel(application: Application): AndroidViewModel(application), CoroutineScope{
    val habitLD = MutableLiveData<ArrayList<Habit>>()

    val habitLoadErrorLD = MutableLiveData<Boolean>()

    val loadingLD = MutableLiveData<Boolean>()

    private val habitDao = AppDatabase.getDatabase(application).habitDao()
    private var job = Job()

    fun loadHabit() {
        loadingLD.postValue(true)
        habitLoadErrorLD.postValue(false)

        launch {
            val result = ArrayList(habitDao.getAll())
            habitLD.postValue(result)
            loadingLD.postValue(false)
        }
    }

    fun onAdd(habit: Habit) {
        if (habit.progress < habit.goal) {
            habit.progress++
            launch {
                habitDao.update(habit)
                loadHabit()
            }
        }
    }

    fun onSub(habit: Habit) {
        if (habit.progress > 0) {
            habit.progress--
            launch {
                habitDao.update(habit)
                loadHabit()
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}