package by.gulis.library.repositories;

import by.gulis.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book,Integer> {
    List<Book> findByNameStartingWith(String search);
}
