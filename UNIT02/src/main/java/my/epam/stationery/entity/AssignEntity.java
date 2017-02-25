package my.epam.stationery.entity;

import my.epam.stationery.StationeryManager;
import my.epam.stationery.StationeryManager.Assign;

public class AssignEntity implements AbstractEntity<Assign> {
    Long stationeryId;
    Long employeeId;

    @Override
    public Assign build() {
        return new Assign(stationeryId, employeeId);
    }
}
