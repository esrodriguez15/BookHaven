package com.example.bookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookapp.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}