package com.nagarro.training.corejavatraining.models;

import java.util.Objects;

public class CompositeKey {
    private final String id;
    private final String brand;

    public CompositeKey(String id, String brand) {
        this.id = id;
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKey that = (CompositeKey) o;
        return Objects.equals(id, that.id) && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand);
    }

    @Override
    public String toString() {
        return "CompositeKey{" + "id='" + id + '\'' + ", brand='" + brand + '\'' + '}';
    }
}
