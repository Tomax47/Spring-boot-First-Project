package com.example.demo.dao;

import com.example.demo.module.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository("postgres")
public class PersonDataAccessService implements PersonDao{

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public int insertPerson(UUID id, Person person) {
        return 0;
    }

    @Override
    public List<Person> selectAllPeople() {

        //RowMapper : the RowMapper is for how we retrieve the data from our database, and then transform that into a java object!
        //The jdbcTemplate.query() : will help us pass psql commands and execute them!

        final String sql = "SELECT id, name FROM person";
        List<Person> people = jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });

        return people;
    }

    @Override
    public int deletePerson(String name) {
        return 0;
    }

    @Override
    public int updatePerson(UUID id, Person person) {
        return 0;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT id, name FROM person WHERE id = ?";

        //Inside the new Object[]{id}, in the place of "id" we pass the parameters we want (can be id, name etc.. and they can be multiple, and in this case we separate them with a comma!)
        //We used queryForObject cuz we are looking for one person, so this will return a single person, instead of a list of people like it did in the findAllPeople method!
        Person person = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},(resultSet, i) -> {
            UUID PersonId = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");

            return new Person(PersonId, name);
        });

        return Optional.ofNullable(person);
    }
}
