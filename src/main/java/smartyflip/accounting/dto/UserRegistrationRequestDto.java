package smartyflip.accounting.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRegistrationRequestDto {

    @Valid
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!$#%]?)(?!^[0-9]*$)[a-zA-Z0-9!$#%]{3,10}$", message = "Username must be unique, 3-10 characters long, and can include letters, numbers, and !, $, #, %. It cannot be only numbers.")
    @NotNull(message = "Username is mandatory")
    String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!$#%])[A-Za-z\\d!$#%]{8,}$", message = "Password must be 8 or more characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character (!, $, #, %).")
    @NotNull(message = "Password is mandatory")
    String password;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])[A-Za-z]{2,}$", message = "First name must be at least 2 characters long and include both uppercase and lowercase letters, without numbers or special characters.")
    @NotNull(message = "First name is mandatory")
    String firstName;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])[A-Za-z]{2,}$", message = "Last name must be at least 2 characters long and include both uppercase and lowercase letters, without numbers or special characters.")
    @NotNull(message = "Last name is mandatory")
    String lastName;

    @NotBlank(message = "Email has invalid format")
    @NotNull(message = "Email is mandatory")
    @Pattern(regexp ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "Email has invalid format")
    String email;

    public String getEmail() {
        return email.toLowerCase();
    }
}
