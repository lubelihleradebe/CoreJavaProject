package com.nagarro.training.corejavatraining.Interfaces;


import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.nagarro.training.corejavatraining.models.CompositeKey;
import com.nagarro.training.corejavatraining.models.Product;

public interface FilterStrategy {
    List<Product> filter(Map<String, String> criteria, ConcurrentMap<CompositeKey, Product> productMap);
}