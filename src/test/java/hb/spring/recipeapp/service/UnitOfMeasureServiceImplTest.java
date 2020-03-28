package hb.spring.recipeapp.service;

import hb.spring.recipeapp.commands.UnitOfMeasureCommand;
import hb.spring.recipeapp.converters.UnitOfMeasureToCommand;
import hb.spring.recipeapp.domain.UnitOfMeasure;
import hb.spring.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;

    UnitOfMeasureToCommand unitOfMeasureToCommand = new UnitOfMeasureToCommand();
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToCommand);
    }

    @Test
    void listAllUoms() throws Exception {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        unitOfMeasures.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        unitOfMeasures.add(uom2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms();
        assertEquals(2, unitOfMeasureCommands.size());
        verify(unitOfMeasureRepository,times(1)).findAll();
    }
}