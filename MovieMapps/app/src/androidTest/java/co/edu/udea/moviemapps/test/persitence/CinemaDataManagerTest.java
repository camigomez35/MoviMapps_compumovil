package co.edu.udea.moviemapps.test.persitence;


import android.database.sqlite.SQLiteConstraintException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import co.edu.udea.moviemapps.model.Cinema;
import co.edu.udea.moviemapps.model.User;
import co.edu.udea.moviemapps.persistence.CinemaDataManager;
import co.edu.udea.moviemapps.persistence.UserDataManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class CinemaDataManagerTest {
    CinemaDataManager cinemaDataManager;

    @Before
    public void setUp() {
        cinemaDataManager =  CinemaDataManager.getInstance();
    }

    @Test
    public void testGetCinemas() {
        List<Cinema> Cinemas = cinemaDataManager.getAllCinemas();
        assertEquals(4 , Cinemas.size());
    }

    public static class UserDataManagerTest {

        UserDataManager userDataManager = UserDataManager.getInstance();
        User existentUser = new User();
        User inexistentUser = new User();
        long idInexistentUser = 25698;
        String emailInexistentUser = "saamuelarenas@hotmail.com";
        String emailExistentUser = "brian.uribe93@gmail.com";
        long idExistentUser = 96854;

        @Before
        public void setup() {

            inexistentUser.setId(idInexistentUser);
            inexistentUser.setEmail(emailInexistentUser);
            existentUser.setId(idExistentUser);
            existentUser.setEmail(emailExistentUser);
            userDataManager.insert(existentUser);
        }

        @After
        public void tearDown() {
            if (userDataManager.getUserById(idInexistentUser) != null) {
                userDataManager.delete(inexistentUser);
            }
            if (userDataManager.getUserById(idExistentUser) != null) {
                userDataManager.delete(existentUser);
            }
        }

        @Test
        public void insertNewUser() {
            inexistentUser.setId(idInexistentUser);
            long idNew = userDataManager.insert(inexistentUser);
            assertEquals(idNew, inexistentUser.getId());
        }

        @Test(expected = SQLiteConstraintException.class)
        public void testInsertExistentUser() {
            existentUser.setId(idExistentUser);
            long idNew = userDataManager.insert(existentUser);
            assertEquals(idNew, existentUser.getId());
        }

        @Test
        public void updateMovie() {
            existentUser = userDataManager.getUserById(idExistentUser);
            String emailTest = "sebasj14@gmail.com";
            existentUser.setEmail(emailTest);
            userDataManager.update(existentUser);
            existentUser = userDataManager.getUserById(idExistentUser);
            assertEquals(emailTest, existentUser.getEmail());
        }

        @Test
        public void updateInexistentUser() {
            inexistentUser.setEmail("carito9994@gmail.com");
            userDataManager.update(inexistentUser);
            inexistentUser = userDataManager.getUserById(idInexistentUser);
            assertNull(inexistentUser);
        }

        @Test
        public void getMovie() {
            existentUser.setId(idExistentUser);
            existentUser.setEmail(emailExistentUser);
            existentUser = userDataManager.getUserById(idExistentUser);
            assertEquals(emailExistentUser, existentUser.getEmail());
        }

        @Test
        public void deleteExistentUser() {
            int deletedItems = userDataManager.delete(existentUser);
            assertEquals(1, deletedItems);
        }

        @Test
        public void deleteInexistentMovie() {
            int deletedItems = userDataManager.delete(inexistentUser);
            assertEquals(0, deletedItems);
        }

        @Test
        public void getExistentUser() {
            existentUser = userDataManager.getUserById(idExistentUser);
            assertNotNull(existentUser);
        }
    }
}
