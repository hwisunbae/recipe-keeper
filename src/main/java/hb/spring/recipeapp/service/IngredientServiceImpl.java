package hb.spring.recipeapp.service;

import hb.spring.recipeapp.commands.IngredientCommand;
import hb.spring.recipeapp.converters.IngredientToCommand;
import hb.spring.recipeapp.domain.Ingredient;
import hb.spring.recipeapp.domain.Recipe;
import hb.spring.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToCommand command;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToCommand command, RecipeRepository recipeRepository) {
        this.command = command;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent())
            log.error(("recipe id not found. Id:" + recipeId));

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredient -> command.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent())
            log.error("Ingredient id not found. Id:" + id);

        return ingredientCommandOptional.get();
    }
}
