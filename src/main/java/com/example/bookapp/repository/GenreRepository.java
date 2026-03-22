package com.example.bookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookapp.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}