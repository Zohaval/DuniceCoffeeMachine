package org.example;

public enum CoffeeRecipe {

    ESPRESSO(100, 20, 50, "Эспрессо"),
    CAPPUCCINO(150, 50, 50, "Капучино");

    private final int water;
    private final int milk;
    private final int coffee;
    private final String  name;

    CoffeeRecipe(int water, int milk, int coffee, String name) {
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.name = name;
    }

    public int getWater() {
        return water;
    }
    public int getMilk() {
        return milk;
    }
    public int getCoffee() {
        return coffee;
    }
    public String getName() {
        return name;
    }
}
