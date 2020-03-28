package hb.spring.recipeapp.service;

import hb.spring.recipeapp.commands.IngredientCommand;
import hb.spring.recipeapp.converters.CommandToIngredient;
import hb.spring.recipeapp.converters.IngredientToCommand;
import hb.spring.recipeapp.domain.Ingredient;
import hb.spring.recipeapp.domain.Recipe;
import hb.spring.recipeapp.repositories.RecipeRepository;
import hb.spring.recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToCommand ingredientToCommand;
    private final CommandToIngredient commandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToCommand ingredientToCommand,
                                 CommandToIngredient commandToIngredient,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToCommand = ingredientToCommand;
        this.commandToIngredient = commandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error(("recipe id not found. Id:" + recipeId));
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredient -> ingredientToCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()) {
            log.error("Ingredient id not found. Id:" + id);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()){
            //TODO : toss error
            log.error("Recipe not found for id" + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand
                                .getUnitOfMeasureCommand().getId())
                        .orElseThrow(() -> new RuntimeException("Unit of measure not found")));
                        // TODO : address this

            } else {
                recipe.addIngredient(commandToIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            return ingredientToCommand.convert(savedRecipe.getIngredients()
                .stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst()
                .get());
        }
    }
}
