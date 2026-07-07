package com.gukguk.habittracker.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit")
    fun getAll(): List<Habit>

    @Update
    fun update(habit: Habit)

    @Insert
    fun insert(habit: Habit)

    @Delete
    fun delete(habit: Habit)
}