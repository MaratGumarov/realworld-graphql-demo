package com.realworld.graphqlapi.model;

public enum Label {
    SPORT("Sport", "Sport news", "SPRT"),
    POLITICS("Politics", "Politics news", "PLT"),
    SCIENCE("Science", "Science new", "SNC"),
    WORLD("World", "World news", "WRD"),
    OTHER("Other", "Other news", "OTH");

    private String name;
    private String description;
    private String shortName;

    Label(String name, String description, String shortName) {
        this.name = name;
        this.description = description;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getShortName() {
        return shortName;
    }
}

