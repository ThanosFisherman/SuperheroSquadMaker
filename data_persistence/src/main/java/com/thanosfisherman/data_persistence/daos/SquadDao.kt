package com.thanosfisherman.data_persistence.daos

import androidx.room.*
import com.thanosfisherman.data_persistence.models.SquadEntity

@Dao
interface SquadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroToSquad(entity: SquadEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHeroInSquad(squadEntity: SquadEntity): Int

    @Delete
    suspend fun deleteHeroFromSquad(squadEntity: SquadEntity)

    @Query("DELETE FROM SQUAD WHERE id = :id")
    suspend fun deleteHeroFromSquadById(id: Long)

    @Query("SELECT * FROM SQUAD")
    suspend fun findAllHeroesInSquad(): List<SquadEntity>

    @Query("SELECT * FROM SQUAD WHERE id == :id")
    suspend fun findHeroById(id: Long): SquadEntity?

    @Query("SELECT * FROM SQUAD WHERE name == :name")
    suspend fun findHeroByName(name: String): List<SquadEntity>
}