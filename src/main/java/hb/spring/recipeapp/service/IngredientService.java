package hb.spring.recipeapp.service;

import hb.spring.recipeapp.commands.IngredientCommand;
import hb.spring.recipeapp.domain.Ingredient;
import org.springframework.stereotype.Service;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndId(Long recipeId, Long id);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
