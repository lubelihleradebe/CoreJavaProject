package com.nagarro.training.corejavatraining.ConcreteStrategies;

import com.nagarro.training.corejavatraining.interfaces.FilePatternStrategy;

public class NikeFilePattern implements FilePatternStrategy{

    @Override
    public String getFilePattern() {
        return "nike-//d+//.csv";
    }

}
