package com.thanosfisherman.data_persistence.daos

import androidx.room.*
import com.thanosfisherman.data_persistence.models.HeroEntity

@Dao
interface HeroesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(entity: HeroEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHeroes(entities: List<HeroEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHero(heroEntity: HeroEntity): Int

    @Query("SELECT * FROM HEROES")
    suspend fun findAllHeroes(): List<HeroEntity>

    @Query("SELECT * FROM HEROES WHERE id == :id")
    suspend fun findHeroById(id: Long): HeroEntity?

    @Query("SELECT * FROM HEROES WHERE name == :name")
    suspend fun findHeroByName(name: String): List<HeroEntity>
}