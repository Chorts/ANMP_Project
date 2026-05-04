package com.gukguk.habittracker.model

data class Habit(
    var name: String?,
    var description: String?,
    var unit: String?,
    var goal: Int,
    var progress: Int,
    val imageID: Int
)