package com.thanosfisherman.data_persistence.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class HeroesDatabase : RoomDatabase() {


    companion object {
        @Volatile
        private var INSTANCE: HeroesDatabase? = null

        fun getInstance(context: Context): HeroesDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): HeroesDatabase {
            return Room.databaseBuilder(context, HeroesDatabase::class.java, "heroes_db").build()
        }
    }
}