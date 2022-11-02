package com.academy.peach.network.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.academy.peach.network.database.dao.CharacterDao
import com.academy.peach.network.database.model.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class RickAndMortyDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}