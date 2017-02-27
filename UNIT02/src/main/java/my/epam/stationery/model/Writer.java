package my.epam.stationery.model;

import my.epam.stationery.entity.AbstractEntity;

import java.awt.*;

public class Writer extends Stationery{
    private final Color brushColor;

    public Writer(Color brushColor, String brandName, String type, String label) {
        super(brandName, type, label);
        this.brushColor = brushColor;
    }

    protected Writer(Long id, Color brushColor, String brandName, String type, String label, Long price) {
        super(id, brandName, type, label, price);
        this.brushColor = brushColor;
    }

    static class Entity implements AbstractEntity<Writer> {
        private Long id;
        private Color brushColor;
        private Color shellColor;
        private String brandName;
        private String type;
        private String label;
        private Long price;

        public Entity() {
        }

        @Override
        public Writer build() {
            return new Writer(id, brushColor, brandName, type, label, price);
        }
    }
}
