package com.fburaky.travelbook.model;

import java.io.Serializable;

public class Place implements Serializable {

    public String name;
    public Double latitude;
    public Double longtide;


    public Place(String name, Double latitude, Double longtide) {
        this.name = name;
        this.latitude = latitude;
        this.longtide = longtide;
    }




}
