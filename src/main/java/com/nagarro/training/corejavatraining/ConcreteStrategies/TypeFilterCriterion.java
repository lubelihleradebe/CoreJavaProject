package com.nagarro.training.corejavatraining.ConcreteStrategies;

import com.nagarro.training.corejavatraining.Interfaces.FilterCriterion;
import com.nagarro.training.corejavatraining.models.Product;

public class TypeFilterCriterion implements FilterCriterion{

    @Override
    public boolean matches(Product product, String value) {
        return product.getType().equalsIgnoreCase(value);
    }


}
