package com.example.demo.dao;

import com.example.demo.module.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Here we initialize the operations allowed, or the contract for everyone who wishes to implement this interface!
// The good thing about it, is that late ron we can use dependency injection, so switch between implementation with a single line of code!
public interface PersonDao {

    //This method allows us to insert a person with a given random ID
    int insertPerson(UUID id, Person person);


    //This method allows us to insert a person without a given ID
    default int insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person> selectAllPeople();

    int deletePerson(String name);

    int updatePerson(UUID id, Person person);

    Optional<Person> selectPersonById(UUID id);
}
