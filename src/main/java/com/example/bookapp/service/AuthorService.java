package com.example.bookapp.service;

import com.example.bookapp.model.Author;
import java.util.List;

public interface AuthorService 
{
    List<Author> findAll();
    Author findById(Long id);
    Author save(Author author);
    void deleteById(Long id);

}
