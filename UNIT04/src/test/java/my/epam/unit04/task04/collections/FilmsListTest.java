package my.epam.unit04.task04.collections;

import my.epam.unit04.task04.model.Actor;
import my.epam.unit04.task04.model.Film;
import org.junit.Assert;
import org.junit.Test;

public class FilmsListTest extends Assert {

    @Test
    public void addFilmWithActor() {
        FilmsList films = new FilmsList();

        Film[] expected = new Film[2];
        expected[0] = new Film("Horror 5", new Actor("Michel", "Lubich"));
        expected[1] = new Film("Horror 2", new Actor("Michel", "Lubich"));

        films.addFilm(expected[0]);
        films.addFilm(expected[1]);

        Film[] actual = films.getAllFilms();

        assertArrayEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void addNullShouldThrowNPE() {
        FilmsList films = new FilmsList();

        films.addFilm(null);
    }

    @Test
    public void remove() {
        FilmsList films = new FilmsList();

        Film[] expected = new Film[2];
        expected[0] = new Film("Horror 5", new Actor("Michel", "Lubich"));
        expected[1] = new Film("Horror 2", new Actor("Michel", "Lubich"));
        Film unExpected = new Film("Horror 12", new Actor("Igor", "Voronin"));

        films.addFilm(expected[0]);
        films.addFilm(expected[1]);
        films.addFilm(unExpected);

        films.removeFilm(unExpected);

        Film[] actual = films.getAllFilms();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void getAllActorsTest() {
        FilmsList films = new FilmsList();

        Actor[] expected = new Actor[2];
        expected[0] = new Actor("Michel", "Lubich");
        expected[1] = new Actor("Ivan", "Pupkin");
        films.addFilm(new Film("Horror 5", expected[0]));
        films.addFilm(new Film("Horror 2", expected[1]));

        Actor[] actual = films.getAllActors();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void findActorForFilm() {
        FilmsList films = new FilmsList();

        Actor expected = new Actor("Michel", "Lubich");

        Film film = new Film("Horror 5", expected);
        films.addFilm(film);
        films.addFilm(new Film("Horror 2", new Actor("Ivan", "Pupkin")));

        Actor actual = films.findActorForFilm("Horror 5");

        assertEquals(expected, actual);
    }

    @Test
    public void findFilmsForActors() {
        FilmsList films = new FilmsList();

        Film[] expected = new Film[2];
        Actor actor = new Actor("Michel", "Lubich");
        expected[0] = new Film("Horror 5", actor);
        expected[1] = new Film("Horror 2", actor);

        films.addFilm(expected[0]);
        films.addFilm(expected[1]);

        Film[] actual = films.findFilmsByActor(actor);

        assertArrayEquals(expected, actual);
    }
}

