package by.gulis.library.services;

import by.gulis.library.models.Book;
import by.gulis.library.models.Person;
import by.gulis.library.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int id){
        Optional<Person> person = peopleRepository.findById(id);
        List<Book> bookList;
        if(person.isPresent()){
            Hibernate.initialize(person.get().getBookList());
            bookList = person.get().getBookList();
        }else{
            bookList = Collections.emptyList();
        }
        for (Book b : bookList
             ) {
            if(TimeUnit.DAYS.convert(new Date().getTime() - b.getLastTimeTaken().getTime(),TimeUnit.MILLISECONDS) > 10){
                b.setOverdue(true);
            }
        }
        return bookList;
    }

}
