package my.epam.stationery.model;

import my.epam.stationery.dao.HasId;
import my.epam.stationery.entity.AbstractEntity;

public class Stationery implements HasId {
    private final Long id;
    private final String brandName;
    private final String type;
    private final String label;

    public static final String DEFAULT_BRAND_NAME = "UNBRANDED";
    public static final String DEFAULT_TYPE = "UNDEFINED";
    public static final String DEFAULT_LABEL = "";

    public Stationery(String brandName, String type, String label) {
        this.id = null;
        this.brandName = brandName == null ? DEFAULT_BRAND_NAME : brandName;
        this.type = type == null ? DEFAULT_TYPE : type;
        this.label = label == null ? DEFAULT_LABEL : label;
    }

    Stationery(Long id, String brandName, String type, String label) {
        this.id = id;
        this.brandName = brandName == null ? DEFAULT_BRAND_NAME : brandName;
        this.type = type == null ? DEFAULT_TYPE : type;
        this.label = label == null ? DEFAULT_LABEL : label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stationery that = (Stationery) o;

        return brandName.equals(that.brandName)
                && type.equals(that.type)
                && label.equals(that.label);

    }

    @Override
    public int hashCode() {
        int result = brandName.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + label.hashCode();
        return result;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public Long getId() {
        return id;
    }

    static class Entity implements AbstractEntity<Stationery> {
        Long id;
        String brandName;
        String type;
        String label;

        public Entity() {
        }

        @Override
        public Stationery build() {
            return new Stationery(id, brandName, type, label);
        }
    }
}
