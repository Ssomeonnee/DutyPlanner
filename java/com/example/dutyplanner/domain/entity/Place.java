package com.example.dutyplanner.domain.entity;

public class Place {

    private String name;
    private String description;

    public Place(String name, String description)
    {
        this.name=name;
        this.description=description;
    }

    public void setName(String name){ this.name=name;}
    public void setDescription(String description){ this.description=description;}
    public String getName(){return name;}
    public String getDescription(){return description;}
    public String getWholeDescription(){return name+" - "+description;}
}
