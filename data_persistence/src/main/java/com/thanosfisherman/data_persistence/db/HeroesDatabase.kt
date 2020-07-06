package com.thanosfisherman.data_persistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thanosfisherman.data_persistence.daos.HeroesDao
import com.thanosfisherman.data_persistence.daos.SquadDao
import com.thanosfisherman.data_persistence.models.HeroEntity
import com.thanosfisherman.data_persistence.models.SquadEntity

@Database(entities = [HeroEntity::class, SquadEntity::class], version = 1)
abstract class HeroesDatabase : RoomDatabase() {

    abstract fun heroesDao(): HeroesDao
    abstract fun squadDao(): SquadDao

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