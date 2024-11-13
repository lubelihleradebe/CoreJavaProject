package com.nagarro.training.corejavatraining.ConcreteStrategies;

import com.nagarro.training.corejavatraining.Interfaces.FilterCriterion;
import com.nagarro.training.corejavatraining.models.Product;

public class BrandFilterCriterion implements FilterCriterion{

    @Override
    public boolean matches(Product product, String value) {
        return product.getBrand().equalsIgnoreCase(value);
    }


}
