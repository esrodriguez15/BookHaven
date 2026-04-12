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

import com.example.bookapp.model.Genre;
import com.example.bookapp.service.GenreService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Controller
@RequestMapping("/inventory/genres")
public class GenreController {

	private static final Logger logger = LoggerFactory.getLogger(GenreController.class);
	private final GenreService genreService;

	public GenreController(GenreService genreService) {
		this.genreService = genreService;
	}

	// LIST
	@GetMapping
	public String list(Model model) {
		logger.info("ENTER list()");

		var genres = genreService.findAll();
		model.addAttribute("genres", genres);
		model.addAttribute("genreForm", new GenreForm());

		logger.info("EXIT list() - {} genres returned", genres.size());
		return "genres/list";
	}

	// CREATE
	@PostMapping
	public String create(@Valid @ModelAttribute("genreForm") GenreForm form, BindingResult result, Model model) {
		logger.info("ENTER create() - genre name={}", form.getName());

		if (result.hasErrors()) {
			logger.warn("VALIDATION ERROR in create() - returning genres");

			var genres = genreService.findAll();
			model.addAttribute("genres", genres);

			logger.info("EXIT create() - validation failed");
			return "genres/list";
		}

		Genre savedGenre = genreService.save(new Genre(form.getName().trim()));

		logger.info("EXIT create() - genre created successfully with id={}", savedGenre.getId());
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