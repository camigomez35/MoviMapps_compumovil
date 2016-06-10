package co.edu.udea.moviemapps.test.persitence;

import android.database.sqlite.SQLiteConstraintException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.persistence.MovieDataManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class MovieDataManagerTest {
    MovieDataManager movieDataManager = MovieDataManager.getInstance();
    Movie inexistentMovie = new Movie();
    Movie existentMovie = new Movie();
    int idInexistentMovie = 21312;
    String nameInexistentMovie = "Batman v Superman";
    String nameExistentMovie = "The Jungle Book";
    int idExistentMovie = 15462;

    public MovieDataManagerTest() {
    }

    @Before
    public void setup() {
        inexistentMovie.setId(idInexistentMovie);
        inexistentMovie.setTitle(nameInexistentMovie);
        existentMovie.setId(idExistentMovie);
        existentMovie.setTitle(nameExistentMovie);
        movieDataManager.insert(existentMovie);
    }

    @After
    public void tearDown() {
        if (movieDataManager.getMovieById(idInexistentMovie) != null) {
            movieDataManager.delete(inexistentMovie);
        }
        if (movieDataManager.getMovieById(idExistentMovie) != null) {
            movieDataManager.delete(existentMovie);
        }
    }

    @Test
    public void insertNewMovie() {
        inexistentMovie.setId(idInexistentMovie);
        int idNew = movieDataManager.insert(inexistentMovie);
        assertEquals(idNew,inexistentMovie.getId());
    }

    @Test(expected = SQLiteConstraintException.class)
    public void testInsertExistentMovie() {
        existentMovie.setId(idExistentMovie);
        int idNew = movieDataManager.insert(existentMovie);
        assertEquals(idNew,existentMovie.getId());
    }

    @Test
    public void updateMovie() {
        existentMovie = movieDataManager.getMovieById(idExistentMovie);
        String nameTest = "Civil War";
        existentMovie.setTitle(nameTest);
        movieDataManager.update(existentMovie);
        existentMovie = movieDataManager.getMovieById(idExistentMovie);
        assertEquals(nameTest, existentMovie.getTitle());
    }

    @Test
    public void updateInexistentMovie() {
        inexistentMovie.setTitle("X-Men");
        movieDataManager.update(inexistentMovie);
        inexistentMovie = movieDataManager.getMovieById(idInexistentMovie);
        assertNull(inexistentMovie);
    }

    @Test
    public void getMovie() {
        existentMovie.setId(idExistentMovie);
        existentMovie.setTitle(nameExistentMovie);
        existentMovie = movieDataManager.getMovieById(idExistentMovie);
        assertEquals(nameExistentMovie,existentMovie.getTitle());
    }

    @Test
    public void deleteExistentMovie() {
        int deletedItems = movieDataManager.delete(existentMovie);
        assertEquals(1,deletedItems);
    }

    @Test
    public void deleteInexistentMovie() {
        int deletedItems = movieDataManager.delete(inexistentMovie);
        assertEquals(0,deletedItems);
    }
}
