package by.gulis.library.services;

import by.gulis.library.models.Book;
import by.gulis.library.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {

        this.booksRepository = booksRepository;
    }
    public List<Book> findAll(Integer page, Integer booksPerPage, String field) {
        if (page != null && booksPerPage != null) {
            if ("true".equals(field)) {
                return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearOfProduction"))).getContent();
            } else {
                return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
            }
        } else {
            if ("true".equals(field)) {
                return booksRepository.findAll(Sort.by("yearOfProduction"));
            } else {
                return booksRepository.findAll();
            }
        }
    }

    public List<Book> findByNameStartingWith(String search) {
        return booksRepository.findByNameStartingWith(search);
    }

    public Book findOne(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
}
