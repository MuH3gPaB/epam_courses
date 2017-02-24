package my.epam.stationery.entity;

import my.epam.stationery.model.Stationery;

public class StationeryEntity implements AbstractEntity<Stationery>{
    public long Id;
    public String brandName;
    public String type;
    public String label;

    public StationeryEntity() {
    }

    @Override
    public Stationery build() {
        return new Stationery(brandName, type, label);
    }
}
