package my.epam.stationery.dao;

import my.epam.stationery.model.Assign;
import my.epam.stationery.model.Employee;
import my.epam.stationery.model.Stationery;
import my.epam.stationery.model.StringParser;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDaoTest extends Assert {

    private static EmployeeDao employeeDao;
    private final static String EMPLOYEE_FILE = "./employee.txt";

    @BeforeClass
    public static void loadDao() {
        employeeDao = new EmployeeDao(new File(EMPLOYEE_FILE), new StringParser<>(Employee.class));
    }

    @Test
    public void findByNameTest(){
        Employee vova = new Employee("Vladimir", "Putin", "RF");
        vova = employeeDao.getById(employeeDao.saveOrUpdate(vova));
        Map<String, String> query = new HashMap<String, String>();
        query.put("firstName", "Vladimir");

        Employee[] founded = employeeDao.findBy(query);
        assertTrue(Arrays.asList(founded).contains(vova));
    }

    @AfterClass
    public static void clearDao() {
        File file = new File(EMPLOYEE_FILE);
        if (!file.delete()) {
            fail("Could not delete file " + EMPLOYEE_FILE + " after tests.");
        }
    }

}
