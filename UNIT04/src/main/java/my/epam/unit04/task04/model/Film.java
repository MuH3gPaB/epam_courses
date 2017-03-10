package my.epam.unit04.task04.model;

import java.io.Serializable;
import java.util.Objects;

public class Film implements Serializable{
    private String title;
    private Actor mainActor;

    public Film(String title, Actor mainActor) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(mainActor);
        this.title = title;
        this.mainActor = mainActor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (title != null ? !title.equals(film.title) : film.title != null) return false;
        return mainActor != null ? mainActor.equals(film.mainActor) : film.mainActor == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (mainActor != null ? mainActor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", mainActor=" + mainActor +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Actor getMainActor() {
        return mainActor;
    }

    public void setMainActor(Actor mainActor) {
        this.mainActor = mainActor;
    }
}
