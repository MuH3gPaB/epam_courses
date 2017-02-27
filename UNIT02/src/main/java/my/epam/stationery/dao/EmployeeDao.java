package my.epam.stationery.dao;

import my.epam.stationery.model.Employee;
import my.epam.stationery.model.StringParser;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeDao extends FiledDao<Employee> {
    public EmployeeDao(File dataFile, StringParser<Employee> parser) {
        super(dataFile, parser);
    }

    @Override
    public List<Employee> getAll() {
        return super.getAll();
    }

    @Override
    public Employee getById(long id) {
        return super.getById(id);
    }

    @Override
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    public void remove(Employee obj) {
        super.remove(obj);
    }

    @Override
    public long saveOrUpdate(Employee obj) {
        return super.saveOrUpdate(obj);
    }

    public Employee[] findBy(Map<String, String> valMap) {
        ArrayList<Field> fields = new ArrayList<>();
        for (String fieldName : valMap.keySet()) {
            try {
                Field field = Employee.class.getDeclaredField(fieldName);
                if (!field.isAccessible()) field.setAccessible(true);
                fields.add(field);
            } catch (NoSuchFieldException e) {
                logger.error("Field with name " + fieldName + " not found.");
                throw new IllegalArgumentException("Field with name " + fieldName + " not found.");
            }
        }

        return getAll().stream()
                .filter((employee) -> {
                    boolean ok = true;
                    for (Field field : fields) {
                        try {
                            Object actual = field.get(employee);
                            Object expected = valMap.get(field.getName());
                            ok = actual.equals(expected);
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage());
                        }
                    }
                    return ok;
                })
                .collect(Collectors.toList())
                .toArray(new Employee[0]);
    }
}
