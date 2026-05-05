package com.gukguk.habittracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gukguk.habittracker.model.Habit
import com.gukguk.habittracker.util.FileHelper

class HabitListViewModel(application: Application): AndroidViewModel(application)  {
    val habitLD = MutableLiveData<ArrayList<Habit>>()

    val habitLoadErrorLD = MutableLiveData<Boolean>()

    val loadingLD = MutableLiveData<Boolean>()

    fun loadHabit() {
        loadingLD.value = true
        habitLoadErrorLD.value = false

        val fileHelper = FileHelper(getApplication())
        val json = fileHelper.readFromFile()

        if (json.isNotEmpty()) {
            val sType = object : TypeToken<List<Habit>>() {}.type
            val result = Gson().fromJson<List<Habit>>(json, sType)
            habitLD.value = result as ArrayList<Habit>
        } else {
            habitLD.value = arrayListOf()
        }

        loadingLD.value = false
    }

    fun saveFile(habitList: ArrayList<Habit>) {
        val fileHelper = FileHelper(getApplication())
        val json = Gson().toJson(habitList)
        fileHelper.writeToFile(json)
    }
}