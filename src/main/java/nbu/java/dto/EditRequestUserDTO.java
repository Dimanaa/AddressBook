package nbu.java.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@NoArgsConstructor
@Component
public class EditRequestUserDTO {
    private String username;
    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private String firstname;
    private String lastname;
}
