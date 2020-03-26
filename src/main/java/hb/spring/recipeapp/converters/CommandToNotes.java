package hb.spring.recipeapp.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import hb.spring.recipeapp.commands.NotesCommand;
import hb.spring.recipeapp.domain.Notes;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;

@Component
public class CommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Null
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if(notesCommand == null)
            return null;

        final Notes notes = new Notes();
        notes.setId(notesCommand.getId());
        notes.setRecipeNotes(notesCommand.getRecipeNotes());
        return notes;
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
