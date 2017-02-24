package my.epam.stationery.dao;

import my.epam.stationery.model.Stationery;

public interface StationeryDao {
    Stationery[] getAll();
    Stationery getById(long id);
    void remove(long id);
    void remove(Stationery st);
    void saveOrUpdate(Stationery st);
}
