package co.edu.udea.moviemapps.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.StringBuilderPrinter;

import co.edu.udea.moviemapps.activities.MovieMapps;
import co.edu.udea.moviemapps.model.ServiceResult;
import co.edu.udea.moviemapps.model.User;
import co.edu.udea.moviemapps.persistence.config.DataManager;
import co.edu.udea.moviemapps.rest.MovieMappsService;
import co.edu.udea.moviemapps.rest.MovieMappsServiceI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserDataManager extends DataManager {
    private static UserDataManager instance;

    public static final String TABLE_NAME = "user";

    public static final int COL_ID = 0,
            COL_NAME = 1, COL_EMAIL = 3, COL_PHOTO = 2;


    public static final String[] COLUMNS = {
            "id",
            "name",
            "photo",
            "email"
    };

    private UserDataManager(Context context) {
        super(context);
    }

    public static UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager(MovieMapps.getContext());
        }
        return instance;
    }


    private synchronized User getUsuarioFromCursor(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(COL_ID));
        user.setName(cursor.getString(COL_NAME));
        user.setPhoto(cursor.getString(COL_PHOTO));
        user.setEmail(cursor.getString(COL_EMAIL));
        return user;
    }

    private synchronized ContentValues getContentValues(User user) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMNS[COL_NAME], user.getName());
        contentValues.put(COLUMNS[COL_PHOTO], user.getPhoto());
        contentValues.put(COLUMNS[COL_EMAIL], user.getEmail());
        contentValues.put(COLUMNS[COL_ID], user.getId());

        return contentValues;
    }


    public synchronized long insert(User user) {
        saveService(user);
        long idUser;
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            idUser = db.insertOrThrow(TABLE_NAME, null, getContentValues(user));
            user.setId(idUser);
        }catch (SQLiteConstraintException e){
            db.update(TABLE_NAME,getContentValues(user),"id = ?", new String[]{String.valueOf(user.getId())});
            idUser = user.getId();
        }
        db.close();
        helper.close();
        return idUser;
    }

    private void saveService(User user)
    {
        Log.e("SAVE","INIT");
        Call<ServiceResult> serviceResultCall = MovieMappsService.getInstance().saveUser(user);
        serviceResultCall.enqueue(new Callback<ServiceResult>() {
            @Override
            public void onResponse(Call<ServiceResult> call, Response<ServiceResult> response) {
            }

            @Override
            public void onFailure(Call<ServiceResult> call, Throwable t) {
                Log.e("ERROR",call.toString());
            }
        });
    }

    public synchronized void update(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.update(TABLE_NAME,
                getContentValues(user),
                COLUMNS[COL_ID] + "=?",
                new String[]{String.valueOf(user.getId())}
        );
        db.close();
        helper.close();
    }

    public User getUserById(long e) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS,
                "id = ?", new String[]{String.valueOf(e)}, null, null, COLUMNS[COL_ID]);

        if (cursor.moveToNext()) {
            return getUsuarioFromCursor(cursor);
        }

        cursor.close();
        helper.close();
        return null;
    }


    public User getUser() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS,
                null, null, null, null, COLUMNS[COL_ID]);

        if (cursor.moveToNext()) {
            return getUsuarioFromCursor(cursor);
        }

        cursor.close();
        helper.close();
        return null;
    }

    public int delete(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int deletedItems = db.delete(TABLE_NAME, COLUMNS[COL_ID] + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
        helper.close();
        return  deletedItems;
    }
}



