package co.edu.udea.moviemapps.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.runner.RunWith;

import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.persistence.config.DataManager;


public class MovieDataManager extends DataManager {

    public static final String TABLE_NAME = "movie";

    public static final int COL_ID = 0,
            COL_TITLE = 1,
            COL_OVERVIEW = 2,
            COL_RELEASEDATE = 3,
            COL_POSTER = 4;

    public static final String[] COLUMNS = {
            "id",
            "title",
            "overview",
            "releasedate",
            "poster"
    };

    private MovieDataManager(Context context) {
        super(context);
    }

    public static MovieDataManager getInstance() {
       return  new MovieDataManager(MovieMapps.getContext());
    }

    private synchronized Movie getMovieFromCursor(Cursor cursor) {
        Movie movie = new Movie();
        movie.setId(cursor.getInt(COL_ID));
        movie.setTitle(cursor.getString(COL_TITLE));
        movie.setOverview(cursor.getString(COL_OVERVIEW));
        movie.setPosterPath(cursor.getString(COL_POSTER));
        movie.setReleaseDate(cursor.getString(COL_RELEASEDATE));
        return movie;
    }

    private synchronized ContentValues getContentValues(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNS[COL_ID], movie.getId());
        contentValues.put(COLUMNS[COL_TITLE], movie.getTitle());
        contentValues.put(COLUMNS[COL_OVERVIEW], movie.getOverview());
        contentValues.put(COLUMNS[COL_POSTER], movie.getPosterPath());
        contentValues.put(COLUMNS[COL_RELEASEDATE], movie.getReleaseDate());
        return contentValues;
    }

    public synchronized int insert(Movie movie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int idMovie = (int) db.insertOrThrow(TABLE_NAME, null, getContentValues(movie));
        db.close();
        helper.close();
        movie.setId(idMovie);
        return idMovie;
    }

    public synchronized int update(Movie movie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int updateRecords = db.update(TABLE_NAME,
                getContentValues(movie),
                COLUMNS[COL_ID] + "=?",
                new String[]{String.valueOf(movie.getId())}
        );
        db.close();
        helper.close();
        return updateRecords;
    }

    public Movie getMovieById(int e) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS,
                "id = ?", new String[]{String.valueOf(e)}, null, null, COLUMNS[COL_ID]);

        if (cursor.moveToNext()) {
            return getMovieFromCursor(cursor);
        }

        cursor.close();
        helper.close();
        return null;
    }

    public int delete(Movie movie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int deletedItems = db.delete(TABLE_NAME, COLUMNS[COL_ID] + " = ?", new String[]{String.valueOf(movie.getId())});
        db.close();
        helper.close();
        return deletedItems;
    }
}



