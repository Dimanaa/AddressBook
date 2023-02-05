package nbu.java.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nbu.java.entity.User;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Setter
@Getter
@Component
public class ResponseUserDTO {
    private int id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;

    public ResponseUserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
    }
}
