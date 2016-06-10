package co.edu.udea.moviemapps.test.persistence;


import android.test.AndroidTestCase;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import co.edu.udea.moviemapps.model.Movie;
import co.edu.udea.moviemapps.persistence.MovieDataManager;

@RunWith(MockitoJUnitRunner.class)
public class MovieDataManagerUnitTest extends AndroidTestCase {
    MovieDataManager movieDataManager;
    Movie movie;

    @Before
    public void setUp() {
        movieDataManager = Mockito.mock(MovieDataManager.class);
        movie = new Movie();
        movie.setId(1);
        movie.setTitle("Civil war");
        movie.setOverview("Muy buena");
        when(movieDataManager.getMovieById(1)).thenReturn(movie);
        when(movieDataManager.insert(movie)).thenReturn(1);
        when(movieDataManager.update(movie)).thenReturn(1);
    }

	@Test
	public void testGetInstance() {
		MovieDataManager movieDataManager = MovieDataManager.getInstance();
		assertNotNull(movieDataManager);
	}

	@Test
	public void testGuardar() {
        int idPelicula = movieDataManager.insert(movie);
        assertEquals(1, idPelicula);
    }

	@Test
	public void testUpdate() {
        int filasModificadas = movieDataManager.update(movie);
        assertEquals(1, filasModificadas);
	}

	@Test
	public void testGetMovieById() {
        Movie movieRetornada = movieDataManager.getMovieById(1);
        assertEquals(movie, movieRetornada);
	}
}
