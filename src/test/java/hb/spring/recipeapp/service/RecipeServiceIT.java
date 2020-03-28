package hb.spring.recipeapp.service;

import hb.spring.recipeapp.commands.RecipeCommand;
import hb.spring.recipeapp.converters.CommandToRecipe;
import hb.spring.recipeapp.converters.RecipeToCommand;
import hb.spring.recipeapp.domain.Recipe;
import hb.spring.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceIT {
    public static final String NEW_DESCRIPTION = "Description";

    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeToCommand recipeToCommand;
    @Autowired
    CommandToRecipe commandToRecipe;

    @Transactional
    @Test
    public void testSaveOfDescription() {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToCommand.convert(testRecipe);

        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommend = recipeService.saveRecipeCommand(testRecipeCommand);

        assertEquals(NEW_DESCRIPTION, savedRecipeCommend.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommend.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommend.getCategoryCommands().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommend.getIngredientCommands().size());
    }

}
