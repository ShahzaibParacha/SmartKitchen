package com.smartkitchen.objects;

public enum Allergies {

    LACTOSE("Lactose"),
    EGGS("Eggs"),
    GLUTEN("Gluten"),
    NUTS("Nuts"),
    FISH("Fish"),
    SOY("Soy");

    private final String name;

    Allergies(final String name){
        this.name = name;
    }

    public String getText(){
        return name;
    }
}

