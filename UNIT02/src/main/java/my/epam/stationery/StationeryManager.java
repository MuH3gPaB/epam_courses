package my.epam.stationery;

import my.epam.stationery.model.Employee;
import my.epam.stationery.model.Stationery;

import java.util.ArrayList;

public class StationeryManager {
    private Assign[] assigns = {new Assign(10L,10L)};

    public Stationery[] getForEmployee(Employee employee){
        return null;
    }

    public void addStationery(Stationery stationery, Employee employee){

    }

    public void addStationery(Stationery[] stationeries, Employee employee){

    }

    public void removeStationery(Stationery stationery){

    }

    public void moveStationery(Stationery stationery, Employee newEmployee){

    }

    public void loadData(ArrayList<Assign> assigns) {

    }

    public static class Assign{
        final Long stationeryId;
        final Long employeeId;

        public Assign(Long stationeryId, Long employeeId) {
            this.stationeryId = stationeryId;
            this.employeeId = employeeId;
        }
    }
}
