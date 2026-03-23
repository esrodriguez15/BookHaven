package com.example.bookapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.bookapp.repository.AuthorRepository;
import com.example.bookapp.repository.BookRepository;
import com.example.bookapp.repository.GenreRepository;

@Controller
public class HomeController {

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
		model.addAttribute("bookCount", bookRepo.count());
		model.addAttribute("authorCount", authorRepo.count());
		model.addAttribute("genreCount", genreRepo.count());
		return "home";
	}

	@GetMapping("/about")
	public String about()
	{
		return "about";
	}

	@GetMapping("/contact")
	public String contact()
	{
		return "contact";
	}

	@GetMapping("/about/Esmeralda")
	public String aboutEsmeralda() 
	{
		return "about-Esmeralda";
	}

	@GetMapping("/about/JamesP")
	public String aboutJamesP() 
	{
		return "about-JamesP";
	}

	@GetMapping("/about/JamesS")
	public String aboutJamesS() 
	{
		return "about-JamesS";
	}

}