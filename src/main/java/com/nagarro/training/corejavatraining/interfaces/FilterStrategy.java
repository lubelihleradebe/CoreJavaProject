package com.nagarro.training.corejavatraining.interfaces;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.nagarro.training.corejavatraining.Product;

public interface FilterStrategy {
    List<Product> filter(Map<String, String> criteria, ConcurrentMap<String, Product> productMap);
}
