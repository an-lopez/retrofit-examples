package com.academy.peach.di

import com.academy.peach.interactor.LoadCharactersUseCase
import com.academy.peach.network.NetworkDataSource
import com.academy.peach.network.RetrofitClientImpl
import com.academy.peach.network.RetrofitSingleton
import com.academy.peach.network.RickAndMortyService
import com.academy.peach.repository.CharacterRepository
import com.academy.peach.repository.CharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class CharactersModule {

    @Binds
    abstract fun providesCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    abstract fun providesNetworkDataSource(retrofitClientImpl: RetrofitClientImpl): NetworkDataSource

    companion object{

        @Provides
        fun providesBaseUrl() : String = "https://rickandmortyapi.com/"

        @Provides
        fun providesOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().also { it.setLevel(HttpLoggingInterceptor.Level.BODY) })
                .build()

        @Provides
        fun providesRetrofitInstance(client: OkHttpClient, baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        @Provides
        fun providesRickAndMortyService(retrofit: Retrofit) = retrofit.create<RickAndMortyService>()

    }

}
