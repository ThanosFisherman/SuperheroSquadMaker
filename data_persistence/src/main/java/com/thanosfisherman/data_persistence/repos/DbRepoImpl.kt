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

    override suspend fun getSquad(): DbResultState<List<CharacterModel>> {
        val squad = squadDao.findAllHeroesInSquad()
        return if (squad.isEmpty())
            DbResultState.EmptyError
        else
            DbResultState.Success(squad.map { it.asDomain() })
    }

    override suspend fun addHeroToSquad(characterModel: CharacterModel) {
        val squadEntity = SquadEntity(characterModel.id, characterModel.name, characterModel.description, characterModel.pic)
        squadDao.insertHeroToSquad(squadEntity)
    }

    override suspend fun deleteHeroFromSquad(characterModel: CharacterModel) {
        squadDao.deleteHeroFromSquadById(characterModel.id)
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

    override suspend fun checkIsHeroInSquad(characterModel: CharacterModel): DbResultState<Boolean> {
        val hero = squadDao.findHeroById(characterModel.id)
        return DbResultState.Success(hero != null)
    }


}