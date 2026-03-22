package com.example.bookapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookapp.model.Genre;
import com.example.bookapp.repository.GenreRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Controller
@RequestMapping("/inventory/genres")
public class GenreController {

	private final GenreRepository genreRepo;

	public GenreController(GenreRepository genreRepo) {
		this.genreRepo = genreRepo;
	}

	// LIST
	@GetMapping
	public String list(Model model) {
		model.addAttribute("genres", genreRepo.findAll());
		model.addAttribute("genreForm", new GenreForm());
		return "genres/list";
	}

	// CREATE
	@PostMapping
	public String create(@Valid @ModelAttribute("genreForm") GenreForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("genres", genreRepo.findAll());
			return "genres/list";
		}

		genreRepo.save(new Genre(form.getName().trim()));
		return "redirect:/inventory/genres";
	}

	// DTO
	public static class GenreForm {
		@NotBlank(message = "Genre name is required")
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}