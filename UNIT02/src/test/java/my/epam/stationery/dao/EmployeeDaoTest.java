package my.epam.stationery.dao;

import my.epam.stationery.model.Assign;
import my.epam.stationery.model.Employee;
import my.epam.stationery.model.Stationery;
import my.epam.stationery.model.StringParser;
import org.junit.*;

import java.io.File;
import java.util.*;

public class EmployeeDaoTest extends Assert {

    private static EmployeeDao employeeDao;
    private String employeeFile;

    @Before
    public void loadDao() {
        Random rnd = new Random();
        employeeFile = "./employee_" + rnd.nextInt(1000) + ".txt";
        employeeDao = new EmployeeDao(new File(employeeFile), new StringParser<>(Employee.class));
    }

    @Test
    public void findByNameTest(){
        Employee vova = new Employee("Vladimir", "Putin", "RF");
        vova = employeeDao.getById(employeeDao.saveOrUpdate(vova));
        Map<String, String> query = new HashMap<>();
        query.put("firstName", "Vladimir");

        List<Employee> founded = employeeDao.findBy(query);
        assertTrue(founded.contains(vova));
    }

    @After
    public void clearDao() {
        File file = new File(employeeFile);
        if (!file.delete()) {
            fail("Could not delete file " + employeeFile + " after tests.");
        }
    }

}
