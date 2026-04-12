package com.example.bookapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.bookapp.model.Book;
import com.example.bookapp.repository.BookRepository;
import java.util.List;

@Service
public class BookServiceImplementation  implements BookService
{
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImplementation.class);

    private final BookRepository bookRepository;

    public BookServiceImplementation(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    
    }

    @Override
    public List<Book> findAll()
    {
        logger.info("Fetching all books");
        return bookRepository.findAll();

    }

    @Override
    public Book findById(Long id) {
        logger.info("Fetching book with id {}", id);
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    public Book save(Book book)
    {
        logger.info("Saving book {}", book.getTitle());
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id)
    {
        logger.warn("Deleting book with id {}", id);
        bookRepository.deleteById(id);
    }
}
