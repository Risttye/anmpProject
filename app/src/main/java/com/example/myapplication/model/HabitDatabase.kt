package com.example.myapplication.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Habit::class, User::class], version = 1)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var instance: HabitDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "habitdb"

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                HabitDatabase::class.java,
                DB_NAME
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed default user and habits on first creation
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getInstance(context)
                            database.userDao().insert(User("student", "123", "Student"))
                            database.habitDao().insertAll(
                                Habit("Drink Water", "Stay hydrated throughout the day", 3, 8, "glasses", "Water", "In Progress"),
                                Habit("Exercise", "Daily workout routine", 15, 30, "minutes", "Fitness", "In Progress"),
                                Habit("Read Books", "Expand your knowledge", 20, 20, "pages", "Book", "Completed"),
                                Habit("Meditation", "Mindfulness practice", 0, 10, "minutes", "Meditation", "In Progress")
                            )
                        }
                    }
                })
                .build()

        fun getInstance(context: Context): HabitDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
    }
}
