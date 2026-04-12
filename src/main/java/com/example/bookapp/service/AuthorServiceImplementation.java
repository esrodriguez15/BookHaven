package com.example.bookapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.bookapp.model.Author;
import com.example.bookapp.repository.AuthorRepository;
import java.util.List;

@Service
public class AuthorServiceImplementation  implements AuthorService
{
    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImplementation.class);

    private final AuthorRepository authorRepository;

    public AuthorServiceImplementation(AuthorRepository authorRepository)
    {
        this.authorRepository = authorRepository;
    
    }

    @Override
    public List<Author> findAll()
    {
        logger.info("Fetching all authors");
        return authorRepository.findAll();

    }

    @Override
    public Author findById(Long id) {
        logger.info("Fetching author with id {}", id);
        return authorRepository.findById(id).orElseThrow();
    }

    @Override
    public Author save(Author author)
    {
        logger.info("Saving author with id {}", author.getName());
        return authorRepository.save(author);
    }

    @Override
    public void deleteById(Long id)
    {
        logger.warn("Deleting author with id {}", id);
        authorRepository.deleteById(id);
    }
}

