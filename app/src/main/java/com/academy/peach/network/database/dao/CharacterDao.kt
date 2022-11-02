package com.academy.peach.network.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.academy.peach.network.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characterEntity: CharacterEntity)

    @Insert
    suspend fun insertCharacter(characterEntity: List<CharacterEntity>)

    @Query(
        """
            SELECT * FROM character
        """
    )
    fun getCharacters(): Flow<List<CharacterEntity>>

}