package hb.spring.recipeapp.converters;

import hb.spring.recipeapp.commands.UnitOfMeasureCommand;
import hb.spring.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandToUnitOfMeasureTest {
    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);

    CommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new CommandToUnitOfMeasure();
    }

    @Test
    void convert() {
        UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setId(LONG_VALUE);
        command.setDescription(DESCRIPTION);

        UnitOfMeasure uom = converter.convert(command);

        assertNotNull(uom);
        assertEquals(LONG_VALUE, uom.getId());
        assertEquals(DESCRIPTION, uom.getDescription());

    }
}