package my.epam.unit02.task02;

import my.epam.stationery.StationeryManager;
import my.epam.stationery.dao.AssignDao;
import my.epam.stationery.dao.EmployeeDao;
import my.epam.stationery.dao.StationeryDao;
import my.epam.stationery.model.*;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    private static final StationeryDao stDao =
            new StationeryDao(new File("./stationery.txt"), new StringParser<>(Stationery.class));

    private static final AssignDao aDao =
            new AssignDao(new File("./assign.txt"), new StringParser<>(Assign.class));

    private static final EmployeeDao emplDao =
            new EmployeeDao(new File("./employee.txt"), new StringParser<>(Employee.class));

    private static StationeryManager manager;

    // USE THIS FLAG TO WORK WITH INITED FILES OR CREATE NEW!
    private static final boolean INITED = true;

    public static void main(String[] args) {
        manager = new StationeryManager(stDao, emplDao, aDao);
        if(!INITED) initData();

        Map<String, String> query = new HashMap<String, String>();
        query.put("firstName", "Vladimir");
        List<Employee> vladimirs = manager.findEmployeeBy(query);
        for(Employee vova : vladimirs){
            long summ = 0;
            for(Stationery stationery : manager.getForEmployee(vova)){
                summ+=stationery.getPrice();
            }
            System.out.println("Employee: "+vova.getFirstName() +
                    ", " + vova.getSecondName() + " - total stationeries price: " + summ);
        }
    }

    private static void initData() {
        Pen pen1 = Pen.DEFAULT_BLUE_PEN;
        pen1.setPrice(100);

        Pen pen2 = new Pen(Color.BLACK, Color.CYAN, "BIC", Pen.GEL_INK_PEN_TYPE, "GelInk");
        pen2.setPrice(999);

        Stationery clip = new Stationery("NoName", "clip", "");
        clip.setPrice(10);

        Employee vova = new Employee("Vladimir", "Ivanov", "0999");

        pen1 = (Pen) manager.saveStationery(pen1);
        pen2 = (Pen) manager.saveStationery(pen2);
        clip = manager.saveStationery(clip);

        vova = manager.saveEmployee(vova);

        manager.assignStationery(pen1, vova);
        manager.assignStationery(pen2, vova);
        manager.assignStationery(clip,vova);
    }
}
