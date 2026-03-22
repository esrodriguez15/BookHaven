package com.example.bookapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookapp.model.Author;
import com.example.bookapp.repository.AuthorRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Controller
@RequestMapping("/inventory/authors")
public class AuthorController {

	private final AuthorRepository authorRepo;

	public AuthorController(AuthorRepository authorRepo) {
		this.authorRepo = authorRepo;
	}

	// List All
	@GetMapping
	public String list(Model model) {
		model.addAttribute("authors", authorRepo.findAll());
		model.addAttribute("authorForm", new AuthorForm());
		return "authors/list";
	}

	// Create
	@PostMapping
	public String create(@Valid @ModelAttribute("authorForm") AuthorForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("authors", authorRepo.findAll());
			return "authors/list";
		}

		authorRepo.save(new Author(form.getName().trim()));
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