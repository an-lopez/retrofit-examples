package com.academy.peach.network.network.service

import com.academy.peach.network.model.network.response.Character
import com.academy.peach.network.model.network.response.ModelWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RickAndMortyClient(private val rickAndMortyService: RickAndMortyService) {

    fun getAllCharacters(onNetworkResponse: (response: List<Character>) -> Unit) {
        rickAndMortyService.getAllCharacters().enqueue(object : Callback<ModelWrapper> {
            override fun onResponse(
                call: Call<ModelWrapper>,
                response: Response<ModelWrapper>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        onNetworkResponse(it.results)
                    }
                } else {
                    onNetworkResponse(emptyList())
                }
            }

            override fun onFailure(call: Call<ModelWrapper>, t: Throwable) {
                onNetworkResponse(emptyList())
            }

        })
    }

    fun getAllCharactersSynchronous(onNetworkResponse: (response: List<Character>) -> Unit) {
        val response = rickAndMortyService.getAllCharacters().execute()
        if (response.isSuccessful) {
            response.body()?.let {
                onNetworkResponse(it.results)
            }
        } else {
            onNetworkResponse(emptyList())
        }
    }
}
