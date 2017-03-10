package my.epam.unit04.task04.dao;

import my.epam.unit04.task04.collections.FilmsList;
import my.epam.unit04.task04.model.Actor;
import my.epam.unit04.task04.model.Film;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class FilmsListDaoTest extends Assert {

    @Test
    public void simpleSaveLoadTest() {
        FilmsList expected = new FilmsList();

        expected.addFilm(new Film("Horror 5", new Actor("Michel", "Lubich")));
        expected.addFilm(new Film("Horror 2", new Actor("Ivan", "Pupkin")));

        File fileStorage = new File("./dataForApp/films.dat");
        FilmsListDao dao = new FilmsListDao(new SerialisedFileSingleStorage<>(fileStorage));

        dao.save(expected);

        FilmsList actual = dao.load();

        assertEquals(expected, actual);
    }
}