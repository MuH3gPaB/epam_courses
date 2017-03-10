package my.epam.unit04.task04.collections;

import my.epam.unit04.task04.model.Actor;
import my.epam.unit04.task04.model.Film;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilmsList implements Serializable {
    private ArrayList<Film> data = new ArrayList<>();

    public Film[] findFilmsByActor(Actor actor) {
        Objects.requireNonNull(actor);
        return data.stream()
                .filter((f) -> f.getMainActor().equals(actor))
                .toArray(Film[]::new);
    }

    public Actor findActorForFilm(String filmTitle) {
        Objects.requireNonNull(filmTitle);
        return data.stream()
                .filter((f) -> f.getTitle().equals(filmTitle))
                .collect(Collectors.toList())
                .get(0).getMainActor();
    }

    public Film[] getAllFilms() {
        return data.stream().toArray(Film[]::new);
    }

    public Actor[] getAllActors() {
        return data.stream()
                .map(Film::getMainActor)
                .collect(Collectors.toSet())
                .toArray(new Actor[0]);
    }

    public void addFilm(Film film) {
        Objects.requireNonNull(film);
        data.add(film);
    }

    public void removeFilm(Film film) {
        Objects.requireNonNull(film);
        data.remove(film);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmsList filmsList = (FilmsList) o;

        return data != null ? data.equals(filmsList.data) : filmsList.data == null;

    }

    @Override
    public int hashCode() {
        return data != null ? data.hashCode() : 0;
    }
}
