package nbu.java.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nbu.java.dto.RegisterRequestUserDTO;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    public User(RegisterRequestUserDTO userDTO) {
        this.firstname = userDTO.getFirstname();
        this.lastname = userDTO.getLastname();
        this.username = userDTO.getUsername();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
    }
}
