package com.example.bookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookapp.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}