package co.edu.udea.moviemapps.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import co.edu.udea.moviemapps.R;
import co.edu.udea.moviemapps.fragment.Funciones;
import co.edu.udea.moviemapps.fragment.InfoCompra;
import co.edu.udea.moviemapps.fragment.Movies;
import co.edu.udea.moviemapps.fragment.Login;
import co.edu.udea.moviemapps.fragment.CinemasMap;
import co.edu.udea.moviemapps.fragment.MovieDetail;
import co.edu.udea.moviemapps.listener.OnFragmentInteractionListener;
import co.edu.udea.moviemapps.persistence.UserDataManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        MovieMapps.setUser(UserDataManager.getInstance().getUser());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(Movies.ID, null, true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case(R.id.showtimes):
                setFragment(Movies.ID, null, true);
                break;
            case(R.id.make_plan):
                setFragment(Login.ID, null, true);
                break;
            case(R.id.map):
                setFragment(CinemasMap.ID, null, true);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setFragment(int fragmentId, Bundle parameters, boolean addStack) {
        Fragment f = null;

        switch (fragmentId) {
            case Movies.ID:
                f = Movies.newInstance();
                break;
            case MovieDetail.ID:
                parameters.getString(MovieDetail.MOVIE_ARG_ID);
                f = MovieDetail.newInstance(parameters.getString(MovieDetail.MOVIE_ARG_ID));
                break;
            case Login.ID:
                f = Login.newInstance();
                break;
            case CinemasMap.ID:
                f= CinemasMap.newInstance();
                break;
            case Funciones.ID:
                f = Funciones.newInstance();
                break;
            case InfoCompra.ID:
                f = InfoCompra.newInstance();
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, f, f.getClass().getName()).addToBackStack(f.getClass().getName());
        ft.commit();
    }

}
