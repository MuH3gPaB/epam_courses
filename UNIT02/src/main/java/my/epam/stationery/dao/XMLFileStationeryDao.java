package my.epam.stationery.dao;

import my.epam.stationery.model.Stationery;
import org.apache.log4j.Logger;

import java.io.File;

public class XMLFileStationeryDao implements StationeryDao {
    Logger logger = Logger.getLogger(XMLFileStationeryDao.class);

    private File xmlFile;

    public XMLFileStationeryDao(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    @Override
    public Stationery[] getAll() {
        return new Stationery[0];
    }

    @Override
    public Stationery getById(long id) {
        return null;
    }

    @Override
    public void remove(long id) {

    }

    @Override
    public void remove(Stationery st) {

    }

    @Override
    public void saveOrUpdate(Stationery st) {

    }
}
