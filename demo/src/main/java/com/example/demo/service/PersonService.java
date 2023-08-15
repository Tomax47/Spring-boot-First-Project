package com.example.demo.service;

import com.example.demo.dao.PersonDao;
import com.example.demo.module.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


//Also like we have done with the FakePersonDataAccess class, we will have to tell java that this is a SERVICE class
// n that's ofc by using the annotation @SERVICE or @COMPONENT "Better to be specific!"
@Service
public class PersonService {

    private final PersonDao personDao;

    //The way inject into the constructor is by using the annotation @AUTOWIRED

    /*
       BECAUSE WE CAN HAVE MULTIPLE IMPLEMENTATIONS OF THE INTERFACE "PersonDao"
       => We will have to distinguish between them, by using the @QUALIFIER
     */

    //The @Qualifier lets us have multiple implementations, like in the place of fakeDao we can just change to mongo, sql tec..., without the need to change anything else!!!
    // FE., Here we can change the fakeDao to postgres, and we will have the impl of the postgres from the class PersonDataAccessService used, without the need to change literally anything else!!!
    @Autowired
    public PersonService(@Qualifier("postgres") PersonDao personDao) {
        this.personDao = personDao;
    }

    public int addPerson(Person person) {
        return personDao.insertPerson(person);
    }

    public List<Person> getAllPeople() {
        return personDao.selectAllPeople();
    }

    public void deletePerson(String name) {
        int state = personDao.deletePerson(name);
        if (state == 1) {
            System.out.println("The username "+name+" has been successfully deleted!");
        } else {
            System.out.println("Something went wrong, operation failed!");
        }
    }

    public Optional<Person> findPersonById(UUID id) {
        return personDao.selectPersonById(id);
    }

    public void updatePerson(UUID id, Person person) {
        int state = personDao.updatePerson(id, person);
        if (state == 1) {
            System.out.println("The user's data has been successfully updated!");
        } else {
            System.out.println("Something went wrong, operation failed!");
        }
    }
}
