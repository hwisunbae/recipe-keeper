package hb.spring.recipeapp.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.sun.istack.Nullable;
import hb.spring.recipeapp.commands.RecipeCommand;
import hb.spring.recipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

@Component
public class CommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CommandToCategory commandToCategory;
    private final CommandToIngredient commandToIngredient;
    private final CommandToNotes commandToNotes;

    public CommandToRecipe(CommandToCategory commandToCategory, CommandToIngredient commandToIngredient, CommandToNotes commandToNotes) {
        this.commandToCategory = commandToCategory;
        this.commandToIngredient = commandToIngredient;
        this.commandToNotes = commandToNotes;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if(recipeCommand == null)
            return null;
        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setServings(recipeCommand.getServings());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setNotes(commandToNotes.convert(recipeCommand.getNotesCommand()));
        if(recipeCommand.getCategoryCommands() != null && recipeCommand.getCategoryCommands().size() >0) {
            recipeCommand.getCategoryCommands().forEach(categoryCommand -> {
                recipe.getCategories().add(commandToCategory.convert(categoryCommand));
            });
        }

        if (recipeCommand.getIngredientCommands() != null && recipeCommand.getIngredientCommands().size() > 0) {
            recipeCommand.getIngredientCommands().forEach(ingredientCommand -> {
                recipe.getIngredients().add(commandToIngredient.convert(ingredientCommand));
            });
        }
        return recipe;
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
