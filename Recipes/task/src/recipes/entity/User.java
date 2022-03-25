package recipes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column
    @Pattern(regexp = "\\w+@\\w+\\.\\w+", message = "Email should contain @ and . symbols")
    private String email;

    @Column
    @NotBlank
    @Size(min = 8, message = "Password should contain at least 8 symbols")
    private String password;
}
