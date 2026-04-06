package com.example.bookapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.bookapp.repository.AuthorRepository;
import com.example.bookapp.repository.BookRepository;
import com.example.bookapp.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final BookRepository bookRepo;
	private final AuthorRepository authorRepo;
	private final GenreRepository genreRepo;

	public HomeController(BookRepository bookRepo, AuthorRepository authorRepo, GenreRepository genreRepo) {
		this.bookRepo = bookRepo;
		this.authorRepo = authorRepo;
		this.genreRepo = genreRepo;
	}

	@GetMapping("/")
	public String home(Model model) {
		logger.info("ENTER Home Page");
		model.addAttribute("bookCount", bookRepo.count());
		model.addAttribute("authorCount", authorRepo.count());
		model.addAttribute("genreCount", genreRepo.count());
		return "home";
	}

	@GetMapping("/about")
	public String about()
	{
		logger.info("ENTER About Page");
		return "about";
	}

	@GetMapping("/contact")
	public String contact()
	{
		logger.info("ENTER Contact Page");
		return "contact";
	}

	@GetMapping("/about/Esmeralda")
	public String aboutEsmeralda() 
	{
		logger.info("ENTER About Page - Esmeralda");
		return "about-Esmeralda";
	}

	@GetMapping("/about/JamesP")
	public String aboutJamesP() 
	{
		logger.info("ENTER About Page - JamesP");
		return "about-JamesP";
	}

	@GetMapping("/about/JamesS")
	public String aboutJamesS() 
	{
		logger.info("ENTER About Page - JamesS");
		return "about-JamesS";
	}

}