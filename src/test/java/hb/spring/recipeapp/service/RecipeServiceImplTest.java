package hb.spring.recipeapp.service;

import hb.spring.recipeapp.converters.CommandToRecipe;
import hb.spring.recipeapp.converters.RecipeToCommand;
import hb.spring.recipeapp.domain.Recipe;
import hb.spring.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeService recipeService;
    CommandToRecipe commandToRecipe;
    RecipeToCommand recipeToCommand;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, commandToRecipe, recipeToCommand);
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 1);

        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull(recipeReturned, "Null recipe returned");
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();

    }

    @Test
    public void deleteByIdTest(){
        Long id = Long.valueOf(2L);
        recipeService.deleteById(id);

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}