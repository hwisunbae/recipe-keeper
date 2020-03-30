package hb.spring.recipeapp.commands;

import hb.spring.recipeapp.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    @Lob
    private Byte [] image;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientCommand> ingredientCommands = new HashSet<>();
    private Set<CategoryCommand> categoryCommands = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notesCommand;
}
