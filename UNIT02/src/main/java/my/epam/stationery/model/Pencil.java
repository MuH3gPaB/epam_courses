package my.epam.stationery.model;

import my.epam.stationery.entity.AbstractEntity;

import java.awt.*;

public class Pencil extends Writer{
    private final Long rigitity;

    public static final String SIMPLE_PENCIL_TYPE = "SIMPLE_PENCIL";

    public Pencil(Color brushColor, String brandName, String type, String label, Long rigitity) {
        super(brushColor, brandName, type, label);
        this.rigitity = rigitity;
    }

    public Pencil(Long id, Color brushColor, String brandName, String type, String label, Long price, Long rigitity) {
        super(id, brushColor, brandName, type, label, price);
        this.rigitity = rigitity;
    }

    public long getRigitity() {
        return rigitity;
    }

    static class Entity implements AbstractEntity<Pencil> {
        private Long id;
        private Color brushColor;
        private Long rigidity;
        private String brandName;
        private String type;
        private String label;
        private Long price;

        public Entity() {
        }

        @Override
        public Pencil build() {
            return new Pencil(id, brushColor, brandName, type, label, price, rigidity);
        }
    }
}
