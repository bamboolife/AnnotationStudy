package com.sundy.simple.entity;


import com.sundy.test_annotations.Factory;

/**
 * @author Hannes Dorfmann
 */
@Factory(id = "Calzone", type = Meal.class)
public class CalzonePizza implements Meal {

    @Override
    public float getPrice() {
        return 8.5f;
    }
}
