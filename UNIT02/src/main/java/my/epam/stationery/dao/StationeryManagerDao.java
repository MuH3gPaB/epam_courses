package my.epam.stationery.dao;

import my.epam.stationery.StationeryManager;
import my.epam.stationery.model.StringParser;

import java.io.File;
import java.util.List;

public class StationeryManagerDao extends FiledDao<StationeryManager>{
    public StationeryManagerDao(File dataFile, StringParser<StationeryManager> parser) {
        super(dataFile, parser);
    }

    @Override
    public List<StationeryManager> getAll() {
        return super.getAll();
    }

    @Override
    public StationeryManager getById(long id) {
        return super.getById(id);
    }

    @Override
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    public void remove(StationeryManager obj) {
        super.remove(obj);
    }

    @Override
    public long saveOrUpdate(StationeryManager obj) {
        return super.saveOrUpdate(obj);
    }
}
