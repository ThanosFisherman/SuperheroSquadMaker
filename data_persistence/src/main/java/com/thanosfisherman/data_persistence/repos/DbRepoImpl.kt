package com.thanosfisherman.data_persistence.repos

import com.thanosfisherman.data_persistence.daos.HeroesDao
import com.thanosfisherman.data_persistence.daos.SquadDao
import com.thanosfisherman.data_persistence.models.HeroEntity
import com.thanosfisherman.data_persistence.models.SquadEntity
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.repos.DbRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DbRepoImpl(private val heroesDao: HeroesDao, private val squadDao: SquadDao) : DbRepo {

    override fun getAllHeroes(): Flow<DbResultState<List<CharacterModel>>> = flow {
        val heroes = heroesDao.findAllHeroes()
        if (heroes.isEmpty())
            emit(DbResultState.EmptyError)
        else
            emit(DbResultState.Success(heroes.map { it.asDomain() }))
    }

    override fun getSquad(): Flow<DbResultState<List<CharacterModel>>> = flow {
        val squad = squadDao.findAllHeroesInSquad()
        if (squad.isEmpty())
            emit(DbResultState.EmptyError)
        else
            emit(DbResultState.Success(squad.map { it.asDomain() }))
    }

    override fun addHeroToSquad(characterModel: CharacterModel): Flow<DbResultState<Long>> = flow {
        val squadEntity = SquadEntity(characterModel.id, characterModel.name, characterModel.description, characterModel.pic)
        val id = squadDao.insertHeroToSquad(squadEntity)
        emit(DbResultState.Success(id))
    }

    override fun deleteHeroFromSquad(characterModel: CharacterModel): Flow<DbResultState<Unit>> = flow {
        squadDao.deleteHeroFromSquadById(characterModel.id)
        emit(DbResultState.Success(Unit))
    }

    override fun addHero(characterModel: CharacterModel): Flow<DbResultState<Long>> = flow {
        val heroEntity = HeroEntity(characterModel.id, characterModel.name, characterModel.description, characterModel.pic)
        val id = heroesDao.insertHero(heroEntity)
        emit(DbResultState.Success(id))
    }

    override fun addAllHeroes(heroes: List<CharacterModel>): Flow<DbResultState<Unit>> = flow {
        val heroeEntities = heroes.map { HeroEntity(it.id, it.name, it.description, it.pic) }
        heroesDao.insertAllHeroes(heroeEntities)
        emit(DbResultState.Success(Unit))

    }

    override fun updateSquad(characterModel: CharacterModel): Flow<DbResultState<Int>> = flow {
        val squadEntity = SquadEntity(characterModel.id, characterModel.name, characterModel.description, characterModel.pic)
        val rows = squadDao.updateHeroInSquad(squadEntity)
        emit(DbResultState.Success(rows))
    }
}