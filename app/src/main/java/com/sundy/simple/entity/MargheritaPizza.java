package com.sundy.simple.entity;


import com.sundy.test_annotations.Factory;


@Factory(id = "Margherita", type = Meal.class)
public class MargheritaPizza implements Meal {

    @Override
    public float getPrice() {
        return 6f;
    }
}
