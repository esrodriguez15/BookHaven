package com.example.bookapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookapp.model.Author;
import com.example.bookapp.service.AuthorService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Controller
@RequestMapping("/inventory/authors")
public class AuthorController {

	private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
	private final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	// List All
	@GetMapping
	public String list(Model model) {
		logger.info("ENTER list()");

		var authors = authorService.findAll();
		model.addAttribute("authors", authors);
		model.addAttribute("authorForm", new AuthorForm());

		logger.info("EXIT list() - {} authors returned", authors.size());
		return "authors/list";
	}

	// Create
	@PostMapping
	public String create(@Valid @ModelAttribute("authorForm") AuthorForm form, BindingResult result, Model model) {

		logger.info("ENTER create() - author name={}", form.getName());

		if (result.hasErrors()) {
			logger.warn("VALIDATION ERROR in create() - returning authors");

			var authors = authorService.findAll();
			model.addAttribute("authors", authors);

			logger.info("EXIT create() - validation failed");
			return "authors/list";
		}

		Author savedAuthor = authorService.save(new Author(form.getName().trim()));

		logger.info("EXIT create() - author created successfully with id={}", savedAuthor.getId());
		return "redirect:/inventory/authors";
	}

	// DTO for Author Create
	public static class AuthorForm {
		@NotBlank(message = "Author name is required")
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}