package com.nagarro.training.corejavatraining.ConcreteStrategies;

import com.nagarro.training.corejavatraining.interfaces.FilePatternStrategy;

public class PumaFilePattern implements FilePatternStrategy{

    @Override
    public String getFilePattern() {
        return "puma-//d+//.csv";
    }
}
