package my.epam.stationery.dao;

import my.epam.stationery.model.Stationery;
import org.apache.log4j.Logger;

import java.io.File;

public class XMLFileStationeryDao extends XMLFileDao<Stationery> {


    public XMLFileStationeryDao(File xmlFile) {
        super(xmlFile);
    }

    @Override
    public Stationery[] getAll() {
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
    public void remove(Stationery st) {
        super.remove(st);
    }

    @Override
    public void saveOrUpdate(Stationery st) {
        super.saveOrUpdate(st);
    }
}
