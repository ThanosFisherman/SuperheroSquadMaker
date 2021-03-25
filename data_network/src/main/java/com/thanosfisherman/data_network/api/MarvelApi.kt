package com.thanosfisherman.data_network.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.thanosfisherman.data_network.common.ApiErrorDataModel
import com.thanosfisherman.data_network.models.response.characters.CharactersDataModel
import com.thanosfisherman.data_network.models.response.comics.ComicsDataModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    suspend fun getCharacters(@Query("offset") offset: Int): CharactersDataModel

    @GET("characters/{charId}/comics")
    suspend fun getComicsByCharId(@Path(value = "charId") charId: Long): NetworkResponse<ComicsDataModel, ApiErrorDataModel>
}