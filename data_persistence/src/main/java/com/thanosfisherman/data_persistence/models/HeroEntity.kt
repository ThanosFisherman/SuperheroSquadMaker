package com.thanosfisherman.data_persistence.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thanosfisherman.domain.common.DomainMappable
import com.thanosfisherman.domain.model.CharacterModel

@Entity(tableName = "heroes")
data class HeroEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val description: String,
    val imageUrl: String
) : DomainMappable<CharacterModel> {
    override fun asDomain(): CharacterModel {
        return CharacterModel(
            this.id, this.name, this.description, this.imageUrl
        )
    }
}
