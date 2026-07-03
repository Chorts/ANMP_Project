package com.gukguk.habittracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String?,
    var description: String?,
    var unit: String?,
    var goal: Int,
    var progress: Int,
    val imageID: Int
)