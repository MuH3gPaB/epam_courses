package my.epam.stationery.dao;

import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentScanner;
import org.apache.log4j.Logger;

import java.io.File;

public class XMLFileDao<T extends HasId> implements AbstractDao<T> {
    Logger logger = Logger.getLogger(XMLFileStationeryDao.class);

    private File file;

    public XMLFileDao(File xmlFile) {
        this.file = xmlFile;
    }

    @Override
    public T[] getAll() {
        return null;
    }

    @Override
    public T getById(long id) {
        return null;
    }

    @Override
    public void remove(long id) {

    }

    @Override
    public void remove(T obj) {

    }

    @Override
    public void saveOrUpdate(T obj) {
        long id = obj.getId();
    }
}
