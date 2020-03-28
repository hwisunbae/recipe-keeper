package hb.spring.recipeapp.service;

import hb.spring.recipeapp.commands.IngredientCommand;
import hb.spring.recipeapp.converters.CommandToIngredient;
import hb.spring.recipeapp.converters.CommandToRecipe;
import hb.spring.recipeapp.converters.IngredientToCommand;
import hb.spring.recipeapp.converters.UnitOfMeasureToCommand;
import hb.spring.recipeapp.domain.Ingredient;
import hb.spring.recipeapp.domain.Recipe;
import hb.spring.recipeapp.repositories.RecipeRepository;
import hb.spring.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    IngredientToCommand ingredientToCommand = new IngredientToCommand(new UnitOfMeasureToCommand());
    IngredientService ingredientService;
    CommandToIngredient commandToIngredient;


    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    // needed to init converters
    public IngredientServiceImplTest() {
//        this.ingredientToCommand = new IngredientToCommand(new UnitOfMeasureToCommand());
    }

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToCommand, commandToIngredient, recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndId(){

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(2L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(3L);

        recipe.addIngredient(ingredient);
        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndId(1L, 3L);

        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());



    }

//    @Test
//    public void saveIngredientCommand() {
//        IngredientCommand ingredientCommand = new IngredientCommand();
//        ingredientCommand.setId(3L);
//        ingredientCommand.setRecipeId(2L);
//
//        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
//
//        Recipe savedRecipe = new Recipe();
//        savedRecipe.addIngredient(new Ingredient());
//        savedRecipe.getIngredients().iterator().next().setId(3L);
//
//        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
//        when(recipeRepository.save(any())).thenReturn(savedRecipe);
//
//        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);
//
//        assertEquals(Long.valueOf(3L), savedCommand.getId());
//        verify(recipeRepository,times(1)).findById(anyLong());
//        verify(recipeRepository, times(1)).save(any(Recipe.class));
//
//    }
}