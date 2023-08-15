package com.example.demo.dao;

import com.example.demo.module.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


//The way to tell java that this class needs to be instantiated as a bean so we can
// inject it in other classes, is by using the annotations!

@Repository("fakeDao") //Or @COMPONENT, Tho @REPOSITORY makes it obvious that this class is a repository!
/* The ("fakeDao") is to complete the annotation @Qualifier in the PersonService class to allow us to have multiple implementations! */
public class FakePersonDataAccess implements PersonDao{

    private static List<Person> DB = new ArrayList<>();
    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public int deletePerson(String name) {
        for (Person person : DB) {
            if (person.getName().equals(name)) {
                DB.remove(person);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int updatePerson(UUID id, Person update) {
        return selectPersonById(id)
                .map(person -> {int indexPersonToUpdate = DB.indexOf(person);
                    if (indexPersonToUpdate >= 0) {
                        DB.set(indexPersonToUpdate, new Person(id, update.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {

        //The following bloc code return Optional<> datatype!
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

}
