package com.example.myapplication.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    fun getUser(username: String, password: String): User?

    @Query("SELECT COUNT(*) FROM user")
    fun countUsers(): Int
}
