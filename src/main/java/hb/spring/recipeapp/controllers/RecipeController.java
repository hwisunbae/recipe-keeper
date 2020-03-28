package hb.spring.recipeapp.controllers;

import hb.spring.recipeapp.commands.RecipeCommand;
import hb.spring.recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("/recipe/add")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeForm";
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeForm";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id){
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

}
