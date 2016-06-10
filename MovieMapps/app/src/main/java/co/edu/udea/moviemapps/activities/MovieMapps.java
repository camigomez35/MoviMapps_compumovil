package co.edu.udea.moviemapps.activities;

import android.app.Application;
import android.content.Context;

import co.edu.udea.moviemapps.model.User;
import co.edu.udea.moviemapps.persistence.UserDataManager;


public class MovieMapps extends Application {
    private static Context context;
    private static User user;

    public static User getUser() {
        return user == null ? new User() : user;
    }

    public static void setUser(User newUser) {
        user = newUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Context getContext() {
        return context;
    }
}
