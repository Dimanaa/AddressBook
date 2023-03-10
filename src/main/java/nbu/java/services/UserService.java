package nbu.java.services;

import nbu.java.dto.EditRequestUserDTO;
import nbu.java.dto.LoginUserDTO;
import nbu.java.dto.RegisterRequestUserDTO;
import nbu.java.dto.ResponseUserDTO;
import nbu.java.entity.User;
import nbu.java.repositories.UserRepository;
import nbu.java.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(int id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public void register(RegisterRequestUserDTO userDTO, BindingResult bindingResult) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            ObjectError error = new ObjectError("global", "Username already exists");
            bindingResult.addError(error);
        }

        String email = userDTO.getEmail();
        String validateEmailMessage = Validator.validateEmail(email);
        if (!validateEmailMessage.isEmpty()) {
            ObjectError error = new ObjectError("global", validateEmailMessage);
            bindingResult.addError(error);
        }

        if (userRepository.findByEmail(email) != null) {
            ObjectError error = new ObjectError("global", "Email already exists");
            bindingResult.addError(error);
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String initialPassword = userDTO.getPassword();
        String validatePasswordMessage = Validator.validatePassword(initialPassword);
        if (!validatePasswordMessage.isEmpty()) {
            ObjectError error = new ObjectError("global", validatePasswordMessage);
            bindingResult.addError(error);
        }

        String confirmPassword = userDTO.getConfirmPassword();
        String validateConfirmPasswordMessage = Validator.validateConfirmPassword(confirmPassword, initialPassword);
        if (!validateConfirmPasswordMessage.isEmpty()) {
            ObjectError error = new ObjectError("global", validateConfirmPasswordMessage);
            bindingResult.addError(error);
        }

        String encodedPassword = encoder.encode(initialPassword);
        userDTO.setPassword(encodedPassword);

        String firstname = userDTO.getFirstname();
        String validateFirstnameMessage = Validator.validatename(firstname);
        if (!validateFirstnameMessage.isEmpty()) {
            ObjectError error = new ObjectError("global", validateFirstnameMessage);
            bindingResult.addError(error);
        }

        String lastname = userDTO.getLastname();
        String validateLastnameMessage = Validator.validatename(lastname);
        if (!validateLastnameMessage.isEmpty()) {
            ObjectError error = new ObjectError("global", validateLastnameMessage);
            bindingResult.addError(error);
        }

        if (!bindingResult.hasErrors()) {
            User user = new User(userDTO);
            userRepository.save(user);
        }
    }

    public ResponseUserDTO login(LoginUserDTO userDTO, BindingResult bindingResult) {
        String message = "Wrong credentials!";
        if (userDTO.getUsername() == null || userDTO.getPassword() == null) {
            ObjectError error = new ObjectError("global", message);
            bindingResult.addError(error);
        }

        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) {
            if (!bindingResult.hasErrors()) {
                ObjectError error = new ObjectError("global", message);
                bindingResult.addError(error);
            }
        } else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(userDTO.getPassword(), user.getPassword())) {
                return new ResponseUserDTO(user);
            } else {
                if (!bindingResult.hasErrors()) {
                    ObjectError error = new ObjectError("global", message);
                    bindingResult.addError(error);
                }
            }
        }
        return null;
    }

    public ResponseUserDTO editUser(EditRequestUserDTO userDTO, User loggedUser, BindingResult bindingResult) {
        String newUsername = userDTO.getUsername();
        if (newUsername != null && (!newUsername.isEmpty()) && userRepository.findByUsername(newUsername) != null) {
            ObjectError error = new ObjectError("global", "Username already exists");
            bindingResult.addError(error);
        } else {
            loggedUser.setUsername(newUsername);
        }

        String newEmail = userDTO.getEmail();
        String validateEmailMessage = Validator.validateEmail(newEmail);
        if (!validateEmailMessage.isEmpty()) {
            ObjectError error = new ObjectError("global", validateEmailMessage);
            bindingResult.addError(error);
        } else if (newEmail != null) {
            if (userRepository.findByEmail(newEmail) != null) {
                ObjectError error = new ObjectError("global", "Email already exists!");
                bindingResult.addError(error);
            } else {
                loggedUser.setEmail(newEmail);
            }
        }

        String currentPassword = userDTO.getCurrentPassword();
        String validatePasswordMessage = Validator.validateEnteredAndActualPasswords(currentPassword, loggedUser);
        if (!validatePasswordMessage.isEmpty()) {
            ObjectError error = new ObjectError("global", validatePasswordMessage);
            bindingResult.addError(error);
        }

        String newPassword = userDTO.getNewPassword();
        String confirmPassword = userDTO.getConfirmPassword();
        String validateNewAndConfirmPasswordMessage = Validator.validateNewAndConfirmPassword(newPassword, currentPassword, confirmPassword, loggedUser);;
        if (!validateNewAndConfirmPasswordMessage.isEmpty()) {
            ObjectError error = new ObjectError("global", validateNewAndConfirmPasswordMessage);
            bindingResult.addError(error);
        }

        String newFirstname = userDTO.getFirstname();
        String validateFirstnameMessage = Validator.validatename(newFirstname);
        if (!validateFirstnameMessage.isEmpty()) {
            ObjectError error = new ObjectError("global", validateFirstnameMessage);
            bindingResult.addError(error);
        } else {
            loggedUser.setFirstname(newFirstname);
        }

        String newLastname = userDTO.getLastname();
        String validateLastnameMessage = Validator.validatename(newLastname);
        if (!validateLastnameMessage.isEmpty()) {
            ObjectError error = new ObjectError("global", validateLastnameMessage);
            bindingResult.addError(error);
        } else {
            loggedUser.setLastname(newLastname);
        }

        loggedUser = userRepository.save(loggedUser);
        return new ResponseUserDTO(loggedUser);
    }

    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return "You have successfully deleted your account";
    }
}
