package com.example.demo.module;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;


public class Person {

    private final UUID id;

    private final String name;


    //The annotations here are to tell the java that we are gon receive properties from the client with the id of "name" and "id"
    // So java be able to convert them into a class!
    public Person(@JsonProperty("id") UUID id,
                  @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public boolean validatePerson() {
        if (name != null && name.length() > 1) {
            return true;
        }
        return false;
    }

}
