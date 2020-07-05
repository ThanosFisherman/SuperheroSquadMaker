package com.thanosfisherman.data_network.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.thanosfisherman.data_network.common.ApiErrorDataModel
import com.thanosfisherman.data_network.models.response.CharactersDataModel
import retrofit2.http.GET

interface MarvelApi {

    @GET("characters")
    suspend fun getCharacters(): NetworkResponse<CharactersDataModel, ApiErrorDataModel>
}