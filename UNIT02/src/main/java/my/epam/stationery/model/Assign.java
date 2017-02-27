package my.epam.stationery.model;

import my.epam.stationery.entity.AbstractEntity;
import my.epam.stationery.entity.HasId;

import java.awt.*;

public class Assign implements HasId {
    private final Long id;
    private Long stationeryId;
    private Long employeeId;

    public Assign(Long stationeryId, Long employeeId) {
        this.id = null;
        this.stationeryId = stationeryId;
        this.employeeId = employeeId;
    }

    private Assign(Long id, Long stationeryId, Long employeeId) {
        this.id = id;
        this.stationeryId = stationeryId;
        this.employeeId = employeeId;
    }

    public Long getStationeryId() {
        return stationeryId;
    }

    public void setStationeryId(Long stationeryId) {
        this.stationeryId = stationeryId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public Long getId() {
        return id;
    }

    static class Entity implements AbstractEntity<Assign> {
        private Long id;
        private Long stationeryId;
        private Long employeeId;

        public Entity() {
        }

        @Override
        public Assign build() {
            return new Assign(id, stationeryId, employeeId);
        }
    }
}
