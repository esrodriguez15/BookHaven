package com.example.bookapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.bookapp.model.Genre;
import com.example.bookapp.repository.GenreRepository;
import java.util.List;

@Service
public class GenreServiceImplementation  implements GenreService
{
    private static final Logger logger = LoggerFactory.getLogger(GenreServiceImplementation.class);

    private final GenreRepository genreRepository;

    public GenreServiceImplementation(GenreRepository genreRepository)
    {
        this.genreRepository = genreRepository;
    
    }

    @Override
    public List<Genre> findAll()
    {
        logger.info("Fetching all genres");
        return genreRepository.findAll();

    }

    @Override
    public Genre findById(Long id) {
        logger.info("Fetching genre with id {}", id);
        return genreRepository.findById(id).orElseThrow();
    }

    @Override
    public Genre save(Genre genre)
    {
        logger.info("Saving genre {}", genre.getName());
        return genreRepository.save(genre);
    }

    @Override
    public void deleteById(Long id)
    {
        logger.warn("Deleting genre with id {}", id);
        genreRepository.deleteById(id);
    }
}

