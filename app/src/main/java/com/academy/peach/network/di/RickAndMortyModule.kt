package com.academy.peach.network.di

import android.content.Context
import androidx.room.Room
import com.academy.peach.network.database.RickAndMortyDatabase
import com.academy.peach.network.database.dao.CharacterDao
import com.academy.peach.network.network.service.RickAndMortyService
import com.academy.peach.network.ui.characters.CharacterRepository
import com.academy.peach.network.ui.characters.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RickAndMortyModule {

    @Binds
    abstract fun bindsCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository

    companion object{
        @Provides
        fun providesRetrofitClient(): Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        @Provides
        fun providesRickAndMortyService(retrofit: Retrofit): RickAndMortyService =
            retrofit.create(RickAndMortyService::class.java)

        @Provides
        fun providesDatabase(@ApplicationContext context: Context): RickAndMortyDatabase = Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            "APP_DATABASE"
        ).build()

        @Provides
        fun providesCharacterDao(database: RickAndMortyDatabase): CharacterDao = database.characterDao()
    }

}