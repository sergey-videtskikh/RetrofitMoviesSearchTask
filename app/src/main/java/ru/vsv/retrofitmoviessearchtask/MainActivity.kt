package ru.vsv.retrofitmoviessearchtask

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsv.retrofitmoviessearchtask.dto.SearchMovieResponseDto
import ru.vsv.retrofitmoviessearchtask.retrofit.RetrofitApiService
import ru.vsv.retrofitmoviessearchtask.retrofit.RetrofitClient

class MainActivity : AppCompatActivity() {
    private val baseUrl = "https://tv-api.com/"
    private val apiKey = "k_zcuw1ytf"
    private val TAG = "MainActivity"

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitApiService = RetrofitClient.getClint(baseUrl)
            .create(RetrofitApiService::class.java)

        var movieAdapter = MovieAdapter(listOf())

        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        recyclerView.adapter = movieAdapter

        val searchBar = findViewById<EditText>(R.id.search_bar)
        val searchButton = findViewById<Button>(R.id.search_button)
        searchButton.setOnClickListener {
            if (searchBar.text.isNotBlank()) {
                val query = searchBar.text.toString()
                Log.d(TAG, "query: $query")
                retrofitApiService.searchMovies(apiKey, query).enqueue(object :
                    Callback<SearchMovieResponseDto> {
                    override fun onResponse(
                        call: Call<SearchMovieResponseDto>,
                        response: Response<SearchMovieResponseDto>
                    ) {
                        Log.d(TAG, "onResponse")
                        movieAdapter = MovieAdapter(response.body()!!.results)
                        movieAdapter.notifyDataSetChanged()
                        recyclerView.adapter = movieAdapter
                    }

                    override fun onFailure(call: Call<SearchMovieResponseDto>, t: Throwable) {
                        Log.d(TAG, "onFailure")
                    }
                })
                searchBar.setText("")
            }
        }


    }
}