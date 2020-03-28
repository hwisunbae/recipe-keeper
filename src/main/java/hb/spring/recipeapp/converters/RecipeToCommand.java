package hb.spring.recipeapp.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.sun.istack.Nullable;
import hb.spring.recipeapp.commands.*;
import hb.spring.recipeapp.domain.Category;
import hb.spring.recipeapp.domain.Ingredient;
import hb.spring.recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

@Component
public class RecipeToCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCommand categoryToCommand;
    private final IngredientToCommand ingredientToCommand;
    private final NotesToCommand notesToCommand;

    public RecipeToCommand(CategoryToCommand categoryToCommand, IngredientToCommand ingredientToCommand, NotesToCommand notesToCommand) {
        this.categoryToCommand = categoryToCommand;
        this.ingredientToCommand = ingredientToCommand;
        this.notesToCommand = notesToCommand;
    }


    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if(recipe==null)
            return null;

        final RecipeCommand command = new RecipeCommand();
        command.setId(recipe.getId());
        command.setDescription(recipe.getDescription());
        command.setPrepTime(recipe.getPrepTime());
        command.setCookTime(recipe.getCookTime());
        command.setServings(recipe.getServings());
        command.setSource(recipe.getSource());
        command.setUrl(recipe.getUrl());
        command.setDirections(recipe.getDirections());
        command.setDifficulty(recipe.getDifficulty());
        command.setNotesCommand(notesToCommand.convert(recipe.getNotes()));

        if(recipe.getCategories() != null && recipe.getCategories().size() >0){
            recipe.getCategories().forEach((Category category) -> {
                command.getCategoryCommands().add(categoryToCommand.convert(category));
            });
        }

        if(recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
            recipe.getIngredients().forEach((Ingredient ingredient) -> {
                command.getIngredientCommands().add(ingredientToCommand.convert(ingredient));
            });
        }
        return command;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
