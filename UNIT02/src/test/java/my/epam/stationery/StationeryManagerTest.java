package my.epam.stationery;

import my.epam.stationery.dao.AssignDao;
import my.epam.stationery.dao.EmployeeDao;
import my.epam.stationery.dao.StationeryDao;
import my.epam.stationery.model.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class StationeryManagerTest extends Assert {
    private static StationeryDao stationeryDao;
    private final static String STATIONERY_FILE = "./stationery.txt";

    private static EmployeeDao employeeDao;
    private final static String EMPLOYEE_FILE = "./employee.txt";

    private static AssignDao assignDao;
    private final static String ASSIGN_MANAGER_FILE = "./assign.txt";

    @BeforeClass
    public static void loadDao() {
        stationeryDao = new StationeryDao(new File(STATIONERY_FILE), new StringParser<>(Stationery.class));
        employeeDao = new EmployeeDao(new File(EMPLOYEE_FILE), new StringParser<>(Employee.class));
        assignDao = new AssignDao(new File(ASSIGN_MANAGER_FILE), new StringParser<>(Assign.class));
    }

    @Test
    public void addSingleStationeryToEmployeeTest() {
        StationeryManager manager = new StationeryManager(stationeryDao, employeeDao, assignDao);
        Pen pen = Pen.DEFAULT_BLACK_PEN;
        pen = (Pen) stationeryDao.getById(stationeryDao.saveOrUpdate(pen));

        Employee ivanov = new Employee("Ivan", "Ivanov", "0920");
        ivanov = employeeDao.getById(employeeDao.saveOrUpdate(ivanov));
        manager.addStationery(pen, ivanov);

        Stationery[] ivanovs = manager.getForEmployee(ivanov);
        assertTrue(Arrays.asList(ivanovs).contains(pen));
    }
}
