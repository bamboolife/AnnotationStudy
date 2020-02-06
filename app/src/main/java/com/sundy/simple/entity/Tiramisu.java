package com.sundy.simple.entity;


import com.sundy.test_annotations.Factory;


@Factory(id = "Tiramisu", type = Meal.class)
public class Tiramisu implements Meal {

    @Override
    public float getPrice() {
        return 4.5f;
    }
}
