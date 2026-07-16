package com.example.myapplication.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg habits: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(habit: Habit)

    @Query("SELECT * FROM habit ORDER BY id ASC")
    fun selectAllHabits(): List<Habit>

    @Query("SELECT * FROM habit WHERE id = :id")
    fun selectHabit(id: Int): Habit?

    @Update
    fun updateHabit(habit: Habit)

    @Delete
    fun deleteHabit(habit: Habit)

    @Query("SELECT COUNT(*) FROM habit")
    fun countHabits(): Int
}
