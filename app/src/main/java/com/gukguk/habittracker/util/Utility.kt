package com.gukguk.habittracker.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.gukguk.habittracker.R
import com.gukguk.habittracker.model.AppDatabase

val DB_NAME = "studentdb"

fun buildDb(context:Context): AppDatabase {
    val db = AppDatabase.buildDatabase(context)
    return db
}

fun createChannel(context:Context, importance:Int,
                  showBadge:Boolean, name:String, description:String) {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelID = "${context.packageName}-" +
                "${context.getString(R.string.app_name)}"
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = description
        channel.setShowBadge(showBadge)

        val manager = context.getSystemService(
            NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}