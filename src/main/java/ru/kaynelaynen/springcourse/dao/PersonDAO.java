package ru.kaynelaynen.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.kaynelaynen.springcourse.models.Person;

import java.util.List;

@Service
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(long id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person VALUES(1, ?, ?)", person.getName(), person.getAge());
    }

    public void update(long id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getAge(), id);
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
