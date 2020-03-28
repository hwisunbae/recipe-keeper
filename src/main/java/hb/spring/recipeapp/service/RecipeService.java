package hb.spring.recipeapp.service;

import hb.spring.recipeapp.commands.RecipeCommand;
import hb.spring.recipeapp.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long l);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
    RecipeCommand findCommandById(Long l);
    void deleteById(Long l);
}
