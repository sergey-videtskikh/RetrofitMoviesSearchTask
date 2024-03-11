package ru.vsv.retrofitmoviessearchtask.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.vsv.retrofitmoviessearchtask.dto.SearchMovieResponseDto

interface RetrofitApiService {
    @GET("/en/API/SearchMovie/{apiKey}/{query}")
    fun searchMovies(
        @Path("apiKey") apiKey: String,
        @Path("query") query: String
    ): Call<SearchMovieResponseDto>
}