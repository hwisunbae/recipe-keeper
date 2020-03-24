package hb.spring.recipeapp.controllers;

import hb.spring.recipeapp.domain.Category;
import hb.spring.recipeapp.domain.UnitOfMeasure;
import hb.spring.recipeapp.repositories.CategoryRepository;
import hb.spring.recipeapp.repositories.UnitOfMeasureRepository;
import hb.spring.recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
