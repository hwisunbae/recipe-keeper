package hb.spring.recipeapp.service;

import hb.spring.recipeapp.commands.RecipeCommand;
import hb.spring.recipeapp.converters.CommandToRecipe;
import hb.spring.recipeapp.converters.RecipeToCommand;
import hb.spring.recipeapp.domain.Recipe;
import hb.spring.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final CommandToRecipe commandToRecipe;
    private final RecipeToCommand recipeToCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, CommandToRecipe commandToRecipe, RecipeToCommand recipeToCommand) {
        this.recipeRepository = recipeRepository;
        this.commandToRecipe = commandToRecipe;
        this.recipeToCommand = recipeToCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("In the service");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long l){
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);
        if(!recipeOptional.isPresent())
            throw new RuntimeException("Recipe Not found");

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = commandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("saved recipe ID is" + savedRecipe.getId());
        return recipeToCommand.convert(savedRecipe);
    }
}
