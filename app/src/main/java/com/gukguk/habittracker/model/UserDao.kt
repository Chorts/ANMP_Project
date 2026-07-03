package com.gukguk.habittracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun register(user: User): Long

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    suspend fun login(username: String, password: String): User?

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    suspend fun findByUsername(username: String): User?
}