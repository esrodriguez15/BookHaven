package com.example.bookapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
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
		logger.info("ENTER list()");

		var books = bookRepo.findAll();
		model.addAttribute("books", books);

		logger.info("EXIT list() - {} books returned", books.size());
		return "books/list";
	}

	// Create Form
	@GetMapping("/new")
	public String createForm(Model model) {
		logger.info("ENTER createForm()");

		var authors = authorRepo.findAll();
		var genres = genreRepo.findAll();

		model.addAttribute("bookForm", new BookForm());
		model.addAttribute("authors", authors);
		model.addAttribute("genres", genres);

		logger.info("EXIT createForm() - {} authors and {} genres loaded", authors.size(), genres.size());
		return "books/form";
	}

	// Create Submission
	@PostMapping
	public String create(@Valid @ModelAttribute("bookForm") BookForm form, BindingResult result, Model model) {
		logger.info("ENTER create() - title={}, isbn={}", form.getTitle(), form.getIsbn());

		if (result.hasErrors()) {
			logger.warn("VALIDATION ERROR in create() - returning books/form");

			model.addAttribute("authors", authorRepo.findAll());
			model.addAttribute("genres", genreRepo.findAll());

			logger.info("EXIT create() - validation failed");
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

		Book savedBook = bookRepo.save(book);

		logger.info("EXIT create() - book created successfully with id={}", savedBook.getId());
		return "redirect:/inventory/books";
	}

	// Edit Form
	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		logger.info("ENTER editForm() - id={}", id);

		Book book = bookRepo.findById(id).orElseThrow();

		BookForm form = new BookForm();
		form.setId(book.getId());
		form.setTitle(book.getTitle());
		form.setIsbn(book.getIsbn());
		form.setQuantity(book.getQuantity());
		form.setAuthorId(book.getAuthor() != null ? book.getAuthor().getId() : null);
		form.setGenreId(book.getGenre() != null ? book.getGenre().getId() : null);

		var authors = authorRepo.findAll();
		var genres = genreRepo.findAll();

		model.addAttribute("bookForm", form);
		model.addAttribute("authors", authors);
		model.addAttribute("genres", genres);

		logger.info("EXIT editForm() - loaded edit form for id={}", id);
		return "books/form";
	}

	// Update
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute("bookForm") BookForm form, BindingResult result,
			Model model) {
		logger.info("ENTER update() - id={}", id);

		if (result.hasErrors()) {
			logger.warn("VALIDATION ERROR in update() - id={}", id);

			model.addAttribute("authors", authorRepo.findAll());
			model.addAttribute("genres", genreRepo.findAll());

			logger.info("EXIT update() - validation failed for id={}", id);
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

		Book savedBook = bookRepo.save(book);

		logger.info("EXIT update() - book updated successfully for id={}", savedBook.getId());
		return "redirect:/inventory/books";
	}

	// Delete
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		logger.info("ENTER delete() - id={}", id);

		bookRepo.deleteById(id);

		logger.info("EXIT delete() - book deleted successfully for id={}", id);
		return "redirect:/inventory/books";
	}
}