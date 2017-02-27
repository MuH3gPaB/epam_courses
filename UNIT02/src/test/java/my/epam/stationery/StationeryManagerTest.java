package my.epam.stationery;

import my.epam.stationery.dao.AssignDao;
import my.epam.stationery.dao.EmployeeDao;
import my.epam.stationery.dao.StationeryDao;
import my.epam.stationery.model.*;
import org.junit.*;

import java.io.File;
import java.util.Arrays;

public class StationeryManagerTest extends Assert {
    private static StationeryDao stationeryDao;
    private final static String STATIONERY_FILE = "./stationery.txt";

    private static EmployeeDao employeeDao;
    private final static String EMPLOYEE_FILE = "./employee.txt";

    private static AssignDao assignDao;
    private final static String ASSIGN_FILE = "./assign.txt";

    @Before
    public void loadDao() {
        stationeryDao = new StationeryDao(new File(STATIONERY_FILE), new StringParser<>(Stationery.class));
        employeeDao = new EmployeeDao(new File(EMPLOYEE_FILE), new StringParser<>(Employee.class));
        assignDao = new AssignDao(new File(ASSIGN_FILE), new StringParser<>(Assign.class));
    }

    @Test
    public void addSingleStationeryToEmployeeShouldOk() {
        StationeryManager manager = new StationeryManager(stationeryDao, employeeDao, assignDao);
        Pen pen = Pen.DEFAULT_BLACK_PEN;
        pen = (Pen) stationeryDao.getById(stationeryDao.saveOrUpdate(pen));

        Employee ivanov = new Employee("Ivan", "Ivanov", "0920");
        ivanov = employeeDao.getById(employeeDao.saveOrUpdate(ivanov));
        manager.addStationery(pen, ivanov);

        Stationery[] ivanovs = manager.getForEmployee(ivanov);
        assertTrue(Arrays.asList(ivanovs).contains(pen));
    }

    @Test
    public void addExistingStationeryToEmployeeShouldThrowIllegalArgumentException() {
        StationeryManager manager = new StationeryManager(stationeryDao, employeeDao, assignDao);
        Pen pen = Pen.DEFAULT_BLACK_PEN;
        pen = (Pen) stationeryDao.getById(stationeryDao.saveOrUpdate(pen));

        Employee ivanov = new Employee("Ivan", "Ivanov", "0920");
        ivanov = employeeDao.getById(employeeDao.saveOrUpdate(ivanov));
        manager.addStationery(pen, ivanov);

        try {
            manager.addStationery(pen, ivanov);
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            return;
        }
        fail();
    }

    @Test
    public void addArrayWithExistingStationeryToEmployeeShouldThrowIllegalArgumentException() {
        StationeryManager manager = new StationeryManager(stationeryDao, employeeDao, assignDao);
        Pen[] pens = new Pen[3];
        pens[0] = Pen.DEFAULT_BLACK_PEN;
        pens[1] = Pen.DEFAULT_BLUE_PEN;
        pens[2] = Pen.DEFAULT_RED_PEN;

        pens[0] = (Pen) stationeryDao.getById(stationeryDao.saveOrUpdate(pens[0]));
        pens[1] = (Pen) stationeryDao.getById(stationeryDao.saveOrUpdate(pens[1]));
        pens[2] = (Pen) stationeryDao.getById(stationeryDao.saveOrUpdate(pens[2]));


        Employee ivanov = new Employee("Ivan", "Ivanov", "0920");
        ivanov = employeeDao.getById(employeeDao.saveOrUpdate(ivanov));
        manager.addStationery(pens[0], ivanov);

        try {
            manager.addStationery(pens, ivanov);
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            return;
        }
        fail();
    }

    @Test
    public void addStationeryArrayToEmployeeShouldOk() {
        StationeryManager manager = new StationeryManager(stationeryDao, employeeDao, assignDao);
        Pen[] pens = new Pen[3];
        pens[0] = Pen.DEFAULT_BLACK_PEN;
        pens[1] = Pen.DEFAULT_BLUE_PEN;
        pens[2] = Pen.DEFAULT_RED_PEN;

        pens[0] = (Pen) stationeryDao.getById(stationeryDao.saveOrUpdate(pens[0]));
        pens[1] = (Pen) stationeryDao.getById(stationeryDao.saveOrUpdate(pens[1]));
        pens[2] = (Pen) stationeryDao.getById(stationeryDao.saveOrUpdate(pens[2]));


        Employee ivanov = new Employee("Ivan", "Ivanov", "0920");
        ivanov = employeeDao.getById(employeeDao.saveOrUpdate(ivanov));
        manager.addStationery(pens, ivanov);

        Stationery[] ivanovs = manager.getForEmployee(ivanov);
        assertTrue(Arrays.asList(ivanovs).contains(pens[0]));
        assertTrue(Arrays.asList(ivanovs).contains(pens[1]));
        assertTrue(Arrays.asList(ivanovs).contains(pens[2]));
    }

    @Test
    public void moveStationeryToOtherEmployeeShouldOk() {
        StationeryManager manager = new StationeryManager(stationeryDao, employeeDao, assignDao);

        Pen pen = Pen.DEFAULT_BLACK_PEN;

        pen = (Pen) stationeryDao.getById(stationeryDao.saveOrUpdate(pen));

        Employee vova = new Employee("Vladimir", "Ivanov", "FSB");
        vova = employeeDao.getById(employeeDao.saveOrUpdate(vova));

        Employee dima = new Employee("Dmitry", "Petrov", "FSB");
        dima = employeeDao.getById(employeeDao.saveOrUpdate(dima));

        manager.addStationery(pen, vova);
        assertEquals(vova, manager.getEmployeeOfStationery(pen));
        manager.moveStationery(pen, dima);
        assertEquals(dima, manager.getEmployeeOfStationery(pen));
    }

    @After
    public void clearDao() {
        File file = new File(ASSIGN_FILE);
        if (!file.delete()) {
            fail("Could not delete file " + ASSIGN_FILE + " after tests.");
        }

        file = new File(STATIONERY_FILE);
        if (!file.delete()) {
            fail("Could not delete file " + STATIONERY_FILE + " after tests.");
        }

        file = new File(EMPLOYEE_FILE);
        if (!file.delete()) {
            fail("Could not delete file " + EMPLOYEE_FILE + " after tests.");
        }
    }
}
