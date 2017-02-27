package my.epam.stationery.dao;

import my.epam.stationery.entity.HasId;
import my.epam.stationery.model.Employee;
import my.epam.stationery.model.StringParser;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class FiledDao<T extends HasId> implements AbstractDao<T> {
    Logger logger = Logger.getLogger(FiledDao.class);
    private long currentId = 0;
    private File dataFile;
    private final static char ID_SEPARATOR = '\u0099';
    private StringParser<T> parser;

    public FiledDao(File dataFile, StringParser<T> parser) {
        this.parser = parser;
        this.dataFile = dataFile;
        currentId = readCurrentId();
        if (currentId == 0) try {
            Files.createFile(dataFile.toPath());
        } catch (IOException e) {
            logger.error("Could not create the file " + dataFile.getName());
        }
    }

    @Override
    public List<T> getAll() {
        return readAllRecords();
    }

    @Override
    public T getById(long id) {
        return findRecordById(id);
    }

    @Override
    public void remove(long id) {
        removeRecord(id);
    }

    @Override
    public void remove(T obj) {
        parser = new StringParser<>(obj.getClass());
        if (obj.getId() != null) {
            remove(obj.getId());
        } else {
            throw new NoSuchElementException("Remove. Element " + obj.toString() + " not found.");
        }

    }

    @Override
    public long saveOrUpdate(T obj) {
        parser = new StringParser<>(obj.getClass());
        boolean isNew = (obj.getId() == null);
        long id = (isNew) ? currentId++ : obj.getId();
        StringBuilder sb = new StringBuilder().append(id).append(ID_SEPARATOR);

        sb.append(parser.parseToWithId(obj, id));
        if (isNew) {
            writeNewRecord(sb.toString());
            writeCurrentId();
        } else {
            rewriteRecord(sb.toString(), id);
        }
        return id;
    }

    @Override
    public T saveOrUpdateAndReturn(T obj) {
        return getById(saveOrUpdate(obj));
    }

    @Override
    public List<T> findBy(Map<String, String> valMap) {
        ArrayList<Field> fields = new ArrayList<>();
        for (String fieldName : valMap.keySet()) {
            try {
                Field field = getParserClass().getDeclaredField(fieldName);
                if (!field.isAccessible()) field.setAccessible(true);
                fields.add(field);
            } catch (NoSuchFieldException e) {
                logger.error("Field with name " + fieldName + " not found.");
                throw new IllegalArgumentException("Field with name " + fieldName + " not found.");
            }
        }

        return getAll().stream()
                .filter((obj) -> {
                    boolean ok = true;
                    for (Field field : fields) {
                        try {
                            Object actual = field.get(obj);
                            Object expected = valMap.get(field.getName());
                            ok = actual.equals(expected);
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage());
                        }
                    }
                    return ok;
                })
                .collect(Collectors.toList());
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

    private T findRecordById(long id) {
        try (BufferedReader br = Files.newBufferedReader(dataFile.toPath())) {
            String line = br.readLine();    // Skip first line with current id
            while ((line = br.readLine()) != null) {
                if (line.contains("" + ID_SEPARATOR)) {
                    String idStr = line.substring(0, line.indexOf(ID_SEPARATOR));
                    long recordId = Long.parseLong(idStr);
                    if (recordId == id) {
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

    private List<T> readAllRecords() {
        try {
            return Files.lines(dataFile.toPath())
                    .filter((line) -> line.contains("" + ID_SEPARATOR))
                    .map(line -> {
                        String toUnparse = line.substring(line.indexOf(ID_SEPARATOR) + 1);
                        return parser.parseFrom(toUnparse);
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error reading data from file " + dataFile.getName());
            throw new RuntimeException("Wrong data file format");
        }
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
        try (BufferedWriter bw = Files.newBufferedWriter(dataFile.toPath(), StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            bw.write(Long.toString(currentId));
            bw.flush();
        } catch (IOException e) {
            logger.error("Error writing to file " + dataFile.getName());
            throw new RuntimeException("Could not write data to file");
        }
    }

    private void writeNewRecord(String str) {
        try (BufferedWriter bw = Files.newBufferedWriter(dataFile.toPath(), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            bw.newLine();
            bw.write(str);
            bw.flush();
        } catch (IOException e) {
            logger.error("Error writing to file " + dataFile.getName());
            throw new RuntimeException("Could not write data to file");
        }
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

    protected Class getParserClass(){
        return parser.getCurrentObjectClass();
    }
}
