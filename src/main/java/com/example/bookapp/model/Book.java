package com.example.bookapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String isbn;

	private int quantity;

	@ManyToOne
	private Author author;

	@ManyToOne
	private Genre genre;

	public Book() {
	}

	public Book(String title, String isbn, int quantity, Author author, Genre genre) {
		this.title = title;
		this.isbn = isbn;
		this.quantity = quantity;
		this.author = author;
		this.genre = genre;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getIsbn() {
		return isbn;
	}

	public int getQuantity() {
		return quantity;
	}

	public Author getAuthor() {
		return author;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
}