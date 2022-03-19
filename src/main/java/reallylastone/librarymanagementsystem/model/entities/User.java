package reallylastone.librarymanagementsystem.model.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private char[] password;
}
