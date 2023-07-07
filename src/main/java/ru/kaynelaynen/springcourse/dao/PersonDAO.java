package ru.kaynelaynen.springcourse.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.kaynelaynen.springcourse.models.Book;
import ru.kaynelaynen.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Service
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, year_of_birthday) VALUES (?, ?)", person.getFullName(),
                person.getYearOfBirthday());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET full_name=?, year_of_birthday=? WHERE id=?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirthday(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name=?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
