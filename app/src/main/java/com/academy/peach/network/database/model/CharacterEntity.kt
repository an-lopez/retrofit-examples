package com.academy.peach.network.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "character",
)
data class CharacterEntity(
    val name: String,
    val image: String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}