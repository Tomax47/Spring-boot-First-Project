package com.example.demo.api;

import com.example.demo.module.Person;
import com.example.demo.service.PersonService;
import jakarta.el.PropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person") /* The api/v1/person is the path we have specified in the Postman application in our client request */
@RestController //The @RestController annotation is to tell java that this is the restController class
// which will has methods and end points that the client can consume!
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    //We want this method to be served as post request, so we use the annotation @PostMapping...

    /*

      Gets => For retrieving data from the server
      Post => Adding data to the server
      Put => Modifying the data in the server
      Delete => Deleting data from the server

     */


    //The @RequestBody is used to tell java to receive the request sent by the client "in our case it's the json payload we are sending using the Postman app!"
    @PostMapping
    public void addPerson(@RequestBody Person person) {

        if (person.validatePerson()) {
            personService.addPerson(person);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @GetMapping
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    @DeleteMapping(path = "{name}")
    public void deletePerson(@PathVariable("name") String name) {
        personService.deletePerson(name);
    }

    //The @PathVariable annotation is used to get the user id from the path when the get request using the Postman client!

    /*
      The (path = "{id}" allows us to have ...../{id}, meaning before the forward slash can be the path and at the end an id will be place!
      While the ("id") with the @PathVariable annotation is to grab that ip and turn it into a UUID -> id variable in the arg
     */
    @GetMapping(path = "{id}")
    public Person getPersonById(@PathVariable("id") UUID id) {
        String userNotFound = "404, User not found!";
        return personService.findPersonById(id)
                .orElseThrow(() -> new PropertyNotFoundException(userNotFound));
    }


    //TODO: CHECK WHAT'S WRONG WITH THE UPDATE PERSON METHOD, IT AINT UPDATING!
    //TODO: REMOVE THE PRINTLN COMMANDS CUZ THEY AINT APPEAR ON THE CLIENTS SIDE "NOT NEED FOR NOW"
    @PutMapping(path = "{id}")
    public void updatePerson(@PathVariable("id") UUID id, @RequestBody Person person) {
        if (person.validatePerson()) {
            personService.updatePerson(id, person);
        } else {
            //TODO: FIND A BETTER WAY TO EXECUTE IT!
            throw new IllegalArgumentException();
        }
    }

}


//The 200 code, means that the request went fine without errors or problems!
