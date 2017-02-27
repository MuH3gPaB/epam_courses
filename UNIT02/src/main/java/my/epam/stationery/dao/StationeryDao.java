package my.epam.stationery.dao;

import my.epam.stationery.model.Stationery;
import my.epam.stationery.model.StringParser;

import java.io.File;
import java.util.List;

public class StationeryDao extends FiledDao<Stationery>{
    public StationeryDao(File dataFile, StringParser<Stationery> parser) {
        super(dataFile, parser);
    }

    @Override
    public List<Stationery> getAll() {
        return super.getAll();
    }

    @Override
    public Stationery getById(long id) {
        return super.getById(id);
    }

    @Override
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    public void remove(Stationery obj) {
        super.remove(obj);
    }

    @Override
    public long saveOrUpdate(Stationery obj) {
        return super.saveOrUpdate(obj);
    }
}
