package com.example.bookapp.service;

import com.example.bookapp.model.Genre;
import java.util.List;

public interface GenreService 
{
    List<Genre> findAll();
    Genre findById(Long id);
    Genre save(Genre genre);
    void deleteById(Long id);
}
