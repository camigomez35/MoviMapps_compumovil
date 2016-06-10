package co.edu.udea.moviemapps.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.model.Cinema;
import co.edu.udea.moviemapps.persistence.config.DataManager;


public class CinemaDataManager extends DataManager {

    public static final String TABLE_NAME = "cinema";

    public static final int COL_ID = 0,
            COL_NAME = 1,
            COL_LATITUDE = 2,
            COL_LONGITUDE = 3;

    public static final String[] COLUMNS = {
            "id",
            "name",
            "latitude",
            "longitude"
    };

    public CinemaDataManager(Context context) {
        super(context);
    }

    public static CinemaDataManager getInstance() {
        return new CinemaDataManager(MovieMapps.getContext());
    }

    private synchronized  Cinema getCinemaFromCursor(Cursor cursor){
        Cinema cinema = new Cinema();
        cinema.setId(cursor.getInt(COL_ID));
        cinema.setName(cursor.getString(COL_NAME));
        cinema.setLatitude(cursor.getDouble(COL_LATITUDE));
        cinema.setLongitude(cursor.getDouble(COL_LONGITUDE));
        return cinema;
    }

    public List<Cinema> getAllCinemas() {
        List<Cinema> cinemas = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMNS[COL_ID]);

        Cinema cinema;
        while (cursor.moveToNext()) {
            cinema = getCinemaFromCursor(cursor);
            cinemas.add(cinema);
        }

        cursor.close();
        helper.close();
        return cinemas;
    }

}