package hb.spring.recipeapp.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.sun.istack.Nullable;
import hb.spring.recipeapp.commands.IngredientCommand;
import hb.spring.recipeapp.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

@Component
public class CommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final CommandToUnitOfMeasure converter;

    public CommandToIngredient(CommandToUnitOfMeasure converter) {
        this.converter = converter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null)
            return null;
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setUom(converter.convert(ingredientCommand.getUnitOfMeasureCommand()));
        return ingredient;
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
