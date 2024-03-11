package ru.vsv.retrofitmoviessearchtask

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.vsv.retrofitmoviessearchtask.dto.MovieDto

class MovieAdapter(private val dataSet: List<MovieDto>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    val TAG = "MovieAdapter"

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coverImage: ImageView
        val title: TextView
        val description: TextView

        init {
            // Define click listener for the ViewHolder's View
            coverImage = view.findViewById(R.id.cover_image)
            title = view.findViewById(R.id.title_text)
            description = view.findViewById(R.id.description)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Log.d(TAG,"position: $position" )
        val movie = dataSet[position]
        Log.d(TAG,"movie: $movie" )
        viewHolder.title.text = movie.title
        viewHolder.description.text = movie.description
        viewHolder.coverImage.setImageResource(R.drawable.ic_launcher_foreground)
        Glide.with(viewHolder.coverImage).load(movie.image).into(viewHolder.coverImage)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}