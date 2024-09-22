package edu.uga.cs1302.quiz;

import java.io.Serializable;

public class Country implements Serializable {
    private String name;
    private String continent;

    //Constructor
    public Country(String name, String continent) {
	this.name = name;
	this.continent = continent;
    }

    public String getName() {
	return name;
    }

    public String getContinent() {
	return continent;
    }

    @Override
    public String toString() {
	return name + " - " + continent;
    }
}
     
