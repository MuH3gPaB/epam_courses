package my.epam.stationery.entity;

import my.epam.stationery.StationeryManager;
import my.epam.stationery.StationeryManager.Assign;

import java.util.ArrayList;

public class StationeryManagerEntity implements AbstractEntity<StationeryManager> {
    private ArrayList<Assign> assigns = new ArrayList<>();

    @Override
    public StationeryManager build() {
        StationeryManager manager = new StationeryManager();
        manager.loadData(assigns);
        return manager;
    }
}

