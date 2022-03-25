package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @JsonIgnore
    private long id;

    @Column
    @JsonIgnore
    private String userName;

    @Column
    @NotBlank(message = "Name should not be blank")
    private String name;

    @Column
    @NotBlank(message = "Description should not be blank")
    private String description;

    @Column
    @NotBlank(message = "Category should not be blank")
    private String category;

    @Column
    private LocalDateTime date;

    @Column
    @NotNull(message = "Ingredients list should not be null")
    @Size(min = 1, message = "Ingredients list should contain at least one element")
    @ElementCollection
    private List<String> ingredients;

    @Column
    @NotNull(message = "Directions list should not be null")
    @Size(min = 1, message = "Directions list should contain at least one element")
    @ElementCollection
    private List<String> directions;
}
