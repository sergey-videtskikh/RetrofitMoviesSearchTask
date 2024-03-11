package ru.vsv.retrofitmoviessearchtask.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.vsv.retrofitmoviessearchtask.dto.SearchMovieResponseDto

interface IMDbApi {
    @GET("/en/API/SearchMovie/{apiKey}/{expression}")
    fun searchMovies(
        @Path("apiKey") apiKey: String,
        @Path("expression") expression: String
    ): Call<SearchMovieResponseDto>
}