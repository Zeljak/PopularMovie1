package com.example.zeljk.popularmovie1;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zeljk.popularmovie1.model.Movie;
import com.squareup.picasso.Picasso;


/**
 * Created by zeljk on 03/04/2018.
 */

/**
 * MovieAdapter exposes a list of movies to a RecyclerView
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    public static final String URL_MOVIE_IMAGE = "http://image.tmdb.org/t/p/w500";
    /*
    * An on-click handler that we've defined to make it easy for an Activity to interface with
    * our RecyclerView
    */
    private final MovieAdapterOnClickHandler mClickHandler;
    private Movie[] mMovieData;

    /**
     * Creates a MovieAdapter
     **/
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView is laid out.
     * Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  When RecyclerView has more than one type of item.
     * @return A new MovieAdapterViewHolder that holds the View for each list itme
     */

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);


        return new MovieAdapterViewHolder(view);

    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder should be updated to represent the contents of the item at th given position in the data set.
     * @param position The position of the item within the adapter`s data set.
     */

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String path = URL_MOVIE_IMAGE + mMovieData[position].getPosterPath();


        Picasso.with(holder.movieImageView.getContext())
                .load(path)
                .placeholder(R.drawable.movie_shape)
                .error(R.drawable.ic_movie_black_24dp)
                .into(holder.movieImageView);
    }

    /**
     * This method simply returns the number of items to display.
     *
     * @return The number of movies available.
     */
    @Override
    public int getItemCount() {
        if (null == mMovieData) {
            return 0;
        } else {
            return mMovieData.length;
        }
    }

    /**
     * This method is used to set the movies on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param movieData The new movie data to be displayed.
     */

    public void setMovieData(Movie[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }


    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(int id, String title, String posterPath, String overview, String voteAverage, double releaseDate);
    }

    /**
     * Cache of the children views for a movie list item
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView movieImageView;

        /**
         * Constructor for the class that accepts
         *
         * @param itemView
         */
        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.list_item_movie_picture);
            itemView.setOnClickListener(this);
        }

        /**
         * This gets called by the child view during a click.
         **/

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            int id = mMovieData[adapterPosition].getId();
            String title = mMovieData[adapterPosition].getTitle();
            String posterPath = mMovieData[adapterPosition].getPosterPath();
            String overview = mMovieData[adapterPosition].getOverview();
            String releaseDate = mMovieData[adapterPosition].getReleaseDate();
            double voteAverage = mMovieData[adapterPosition].getVoteAverage();
            mClickHandler.onClick(id, title, posterPath, overview, releaseDate, voteAverage);


        }
    }
}
