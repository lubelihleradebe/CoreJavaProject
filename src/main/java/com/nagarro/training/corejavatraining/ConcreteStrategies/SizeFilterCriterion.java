package com.nagarro.training.corejavatraining.ConcreteStrategies;

import com.nagarro.training.corejavatraining.Interfaces.FilterCriterion;
import com.nagarro.training.corejavatraining.models.Product;

public class SizeFilterCriterion implements FilterCriterion{

    @Override
    public boolean matches(Product product, String value) {
        return product.getSize().equalsIgnoreCase(value);
    }

}
