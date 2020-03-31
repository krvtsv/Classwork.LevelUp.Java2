package org.levelup.reflection;

import org.levelup.reflection.annotation.RandomString;

public class Car {

    @RandomString(maxLength = 10)
    public String brand;
    private String model;

    private Car(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public Car(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    private void changeModel(String model){
        this.model = model;
    }

    public String getModel() {
        return model;
    }
}

