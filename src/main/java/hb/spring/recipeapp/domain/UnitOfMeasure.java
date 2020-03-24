package hb.spring.recipeapp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

}
