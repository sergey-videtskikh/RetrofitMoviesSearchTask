package ru.vsv.retrofitmoviessearchtask

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsv.retrofitmoviessearchtask.dto.MovieDto
import ru.vsv.retrofitmoviessearchtask.dto.SearchMovieResponseDto
import ru.vsv.retrofitmoviessearchtask.retrofit.IMDbApi

class MainActivity : AppCompatActivity() {
    private val baseUrl = "https://tv-api.com/"
    private val apiKey = "k_zcuw1ytf"
    private val TAG = "MainActivity"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbService = retrofit.create(IMDbApi::class.java)

    private lateinit var searchButton: Button
    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var recyclerView: RecyclerView

    private val movies = ArrayList<MovieDto>()

    private val adapter = MoviesAdapter(movies)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moviesAdapter = MoviesAdapter(movies)

        recyclerView = findViewById(R.id.rv)
        recyclerView.adapter = moviesAdapter

        placeholderMessage = findViewById(R.id.placeholder)
        queryInput = findViewById(R.id.search_bar)
        searchButton = findViewById(R.id.search_button)


        searchButton.setOnClickListener {

            if (queryInput.text.isNotBlank()) {
                val query = queryInput.text.toString()

                imdbService.searchMovies(apiKey, query)
                    .enqueue(object : Callback<SearchMovieResponseDto> {
                        override fun onResponse(
                            call: Call<SearchMovieResponseDto>,
                            response: Response<SearchMovieResponseDto>
                        ) {
                            if (response.code() == 200) {
                                movies.clear()
                                if (response.body()?.results?.isNotEmpty() == true) {
                                    movies.addAll(response.body()?.results!!)
                                    moviesAdapter.notifyDataSetChanged()
                                }
                                if (movies.isEmpty()) {
                                    showMessage(getString(R.string.nothing_found), "")
                                } else {
                                    showMessage("", "")
                                }
                            } else {
                                showMessage(
                                    getString(R.string.something_went_wrong),
                                    response.code().toString()
                                )
                            }
                        }

                        override fun onFailure(call: Call<SearchMovieResponseDto>, t: Throwable) {
                            showMessage(
                                getString(R.string.something_went_wrong),
                                t.message.toString()
                            )
                        }
                    })
                queryInput.setText("")
            }
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            placeholderMessage.visibility = View.VISIBLE
            movies.clear()
            adapter.notifyDataSetChanged()
            placeholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
        }
    }
}