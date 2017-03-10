package my.epam.unit04.task04.dao;

import my.epam.unit04.task04.collections.FilmsList;

import java.util.Objects;

public class FilmsListDao implements SingleObjectDao<FilmsList> {

    private final SingleObjectDao<FilmsList> dao;

    public FilmsListDao(SingleObjectDao<FilmsList> dao) {
        Objects.requireNonNull(dao);
        this.dao = dao;
    }


    @Override
    public FilmsList load() {
        return dao.load();
    }

    @Override
    public void save(FilmsList value) {
        dao.save(value);
    }
}
