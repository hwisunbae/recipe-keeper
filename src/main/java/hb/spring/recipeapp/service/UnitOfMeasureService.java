package hb.spring.recipeapp.service;

import hb.spring.recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;


public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUoms();

}
