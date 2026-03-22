package com.example.bookapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookapp.controller.dto.BookForm;
import com.example.bookapp.model.Author;
import com.example.bookapp.model.Book;
import com.example.bookapp.model.Genre;
import com.example.bookapp.repository.AuthorRepository;
import com.example.bookapp.repository.BookRepository;
import com.example.bookapp.repository.GenreRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/inventory/books")
public class BookController {

	private final BookRepository bookRepo;
	private final AuthorRepository authorRepo;
	private final GenreRepository genreRepo;

	public BookController(BookRepository bookRepo, AuthorRepository authorRepo, GenreRepository genreRepo) {
		this.bookRepo = bookRepo;
		this.authorRepo = authorRepo;
		this.genreRepo = genreRepo;
	}

	// List All
	@GetMapping
	public String list(Model model) {
		model.addAttribute("books", bookRepo.findAll());
		return "books/list";
	}

	// Create Form
	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("bookForm", new BookForm());
		model.addAttribute("authors", authorRepo.findAll());
		model.addAttribute("genres", genreRepo.findAll());
		return "books/form";
	}

	// Create Submission
	@PostMapping
	public String create(@Valid @ModelAttribute("bookForm") BookForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("authors", authorRepo.findAll());
			model.addAttribute("genres", genreRepo.findAll());
			return "books/form";
		}

		Author author = authorRepo.findById(form.getAuthorId()).orElseThrow();
		Genre genre = genreRepo.findById(form.getGenreId()).orElseThrow();

		Book book = new Book();
		book.setTitle(form.getTitle());
		book.setIsbn(form.getIsbn());
		book.setQuantity(form.getQuantity());
		book.setAuthor(author);
		book.setGenre(genre);

		bookRepo.save(book);
		return "redirect:/inventory/books";
	}

	// Edit Form
	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		Book book = bookRepo.findById(id).orElseThrow();

		BookForm form = new BookForm();
		form.setId(book.getId());
		form.setTitle(book.getTitle());
		form.setIsbn(book.getIsbn());
		form.setQuantity(book.getQuantity());
		form.setAuthorId(book.getAuthor() != null ? book.getAuthor().getId() : null);
		form.setGenreId(book.getGenre() != null ? book.getGenre().getId() : null);

		model.addAttribute("bookForm", form);
		model.addAttribute("authors", authorRepo.findAll());
		model.addAttribute("genres", genreRepo.findAll());
		return "books/form";
	}

	// Update
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute("bookForm") BookForm form, BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			model.addAttribute("authors", authorRepo.findAll());
			model.addAttribute("genres", genreRepo.findAll());
			return "books/form";
		}

		Book book = bookRepo.findById(id).orElseThrow();

		Author author = authorRepo.findById(form.getAuthorId()).orElseThrow();
		Genre genre = genreRepo.findById(form.getGenreId()).orElseThrow();

		book.setTitle(form.getTitle());
		book.setIsbn(form.getIsbn());
		book.setQuantity(form.getQuantity());
		book.setAuthor(author);
		book.setGenre(genre);

		bookRepo.save(book);
		return "redirect:/inventory/books";
	}

	// Delete
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		bookRepo.deleteById(id);
		return "redirect:/inventory/books";
	}
}