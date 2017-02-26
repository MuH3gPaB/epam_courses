package my.epam.stationery.dao;

import my.epam.stationery.model.Stationery;
import my.epam.stationery.model.StringParser;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StationeryFileDao implements AbstractDao<Stationery> {
    Logger logger = Logger.getLogger(StationeryFileDao.class);
    private long currentId = 0;
    private File dataFile;
    private final static char ID_SEPARATOR = '\u0099';

    public StationeryFileDao(File dataFile) {
        this.dataFile = dataFile;
        currentId = readCurrentId();
    }

    @Override
    public Stationery[] getAll() {
        return readAllRecords();
    }

    @Override
    public Stationery getById(long id) {
        return findRecordById(id);
    }

    @Override
    public void remove(long id) {
        removeRecord(id);
    }

    private void removeRecord(long id) {
        try {
            List<String> lines = getAllLinesExceptId(id);
            Files.write(dataFile.toPath(), lines);
        } catch (IOException e) {
            logger.error("Error writing to file " + dataFile.getName());
            throw new RuntimeException("Could not write data to file");
        }
    }

    private List<String> getAllLinesExceptId(long id) throws IOException {
        return Files.lines(dataFile.toPath())
                .filter(line -> {
                    if (!line.contains("" + ID_SEPARATOR)) return true;
                    long recordId = Long.parseLong(line.substring(0, line.indexOf(ID_SEPARATOR)));
                    return recordId != id;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Stationery obj) {
        if (obj.getId() != null) {
            remove(obj.getId());
        } else {
            throw new NoSuchElementException("Remove. Element " + obj.toString() + " not found.");
        }

    }

    @Override
    public long saveOrUpdate(Stationery obj) {
        boolean isNew = (obj.getId() == null);
        long id = (isNew) ? currentId++ : obj.getId();
        StringBuilder sb = new StringBuilder().append(id).append(ID_SEPARATOR);

        StringParser<Stationery> parser = new StringParser<>(obj.getClass());
        sb.append(parser.parseToWithId(obj, id));
        if (isNew) {
            writeNewRecord(sb.toString());
            writeCurrentId();
        } else {
            rewriteRecord(sb.toString(), id);
        }
        return id;
    }



    private void rewriteRecord(String str, long id) {
        try {
            List<String> lines = getAllLinesExceptId(id);
            lines.add(str);
            Files.write(dataFile.toPath(), lines);
        } catch (IOException e) {
            logger.error("Error writing to file " + dataFile.getName());
            throw new RuntimeException("Could not write data to file");
        }
    }

    private Stationery findRecordById(long id) {
        try (BufferedReader br = Files.newBufferedReader(dataFile.toPath())) {
            String line = br.readLine();    // Skip first line with current id
            while ((line = br.readLine()) != null) {
                if (line.contains("" + ID_SEPARATOR)) {
                    String idStr = line.substring(0, line.indexOf(ID_SEPARATOR));
                    long recordId = Long.parseLong(idStr);
                    if (recordId == id) {
                        StringParser<Stationery> parser = new StringParser<>(Stationery.class);

                        String toUnparse = line.substring(line.indexOf(ID_SEPARATOR) + 1);

                        return parser.parseFrom(toUnparse);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error reading data from file " + dataFile.getName());
            throw new RuntimeException("Wrong data file format");
        }
        return null;
    }

    private Stationery[] readAllRecords() {
        ArrayList<Stationery> list = new ArrayList<>();
        StringParser<Stationery> parser = new StringParser<>(Stationery.class);
        try (BufferedReader br = Files.newBufferedReader(dataFile.toPath())) {
            String line = br.readLine();        // Skip first line with current id
            while ((line = br.readLine()) != null) {
                if (line.contains("" + ID_SEPARATOR)) {
                    String toUnparse = line.substring(line.indexOf(ID_SEPARATOR) + 1);
                    list.add(parser.parseFrom(toUnparse));
                }
            }
        } catch (IOException e) {
            logger.error("Error reading data from file " + dataFile.getName());
            throw new RuntimeException("Wrong data file format");
        }
        return list.toArray(new Stationery[0]);
    }

    private long readCurrentId() {
        if (!dataFile.exists()) return 0;
        try (BufferedReader br = Files.newBufferedReader(dataFile.toPath())) {
            String firstLine = br.readLine();
            if (firstLine == null || firstLine.isEmpty()) return 0;
            return Long.parseLong(firstLine);
        } catch (IOException e) {
            logger.error("Error reading id from file " + dataFile.getName());
            throw new RuntimeException("Wrong data file format");
        }
    }

    private void writeCurrentId() {
        try (BufferedWriter bw = Files.newBufferedWriter(dataFile.toPath(), StandardOpenOption.WRITE)) {
            bw.write(Long.toString(currentId));
            bw.flush();
        } catch (IOException e) {
            logger.error("Error writing to file " + dataFile.getName());
            throw new RuntimeException("Could not write data to file");
        }
    }

    private void writeNewRecord(String str) {
        try (BufferedWriter bw = Files.newBufferedWriter(dataFile.toPath(), StandardOpenOption.APPEND)) {
            bw.newLine();
            bw.write(str);
            bw.flush();
        } catch (IOException e) {
            logger.error("Error writing to file " + dataFile.getName());
            throw new RuntimeException("Could not write data to file");
        }
    }
}
