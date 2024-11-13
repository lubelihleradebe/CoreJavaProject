package com.nagarro.training.corejavatraining.Interfaces;

import com.nagarro.training.corejavatraining.models.Product;

public interface FilterCriterion {
    boolean matches(Product product, String value);
}
