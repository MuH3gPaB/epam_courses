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

    @Override
    public Employee saveOrUpdateAndReturn(Employee obj) {
        return super.saveOrUpdateAndReturn(obj);
    }

    @Override
    public List<Employee> findBy(Map<String, String> valMap) {
        return super.findBy(valMap);
    }
}
