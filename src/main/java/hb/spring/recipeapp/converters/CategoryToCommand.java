package hb.spring.recipeapp.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.sun.istack.Nullable;
import hb.spring.recipeapp.commands.CategoryCommand;
import hb.spring.recipeapp.domain.Category;
import lombok.Synchronized;
import org.springframework.stereotype.Component;


@Component
public class CategoryToCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category category) {
        if(category == null)
            return null;
        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(category.getId());
        categoryCommand.setDescription(category.getDescription());
        return categoryCommand;
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
