package my.epam.unit04.task04.dao;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Implementation of single object storage.
 *
 * Store serializable object into file.
 *
 * @param <T> Type of storing ibject. Should be serializable.
 */

public class SerialisedFileSingleStorage<T extends Serializable> implements SingleObjectDao<T> {
    private static Logger logger = Logger.getLogger(SerialisedFileSingleStorage.class);
    private final File file;

    public SerialisedFileSingleStorage(File file) {
        this.file = file;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T load() {
        try (InputStream inputStream = Files.newInputStream(file.toPath(), StandardOpenOption.READ);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (T) objectInputStream.readObject();
        } catch (IOException e) {
            logger.error("Could not load object from file [" + file.getName() + "]");
        } catch (ClassNotFoundException e) {
            logger.error("Class for object from file [" + file.getName() + "] not found");
        } catch (ClassCastException e) {
            logger.error("Object from file ["+file.getName()+"] has wrong type.");
        }
        return null;
    }

    @Override
    public void save(T value) {
        try (OutputStream outputStream = Files.newOutputStream(file.toPath(), StandardOpenOption.CREATE);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(value);
        } catch (IOException e) {
            logger.error("Could not save object [" + value + "]to file [" + file.getName() + "]");
        }
    }
}
