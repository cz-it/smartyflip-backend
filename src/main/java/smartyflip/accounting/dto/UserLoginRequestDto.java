package smartyflip.accounting.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    @Valid
    @NotBlank(message = "Username is mandatory")
    @NotNull(message = "Username is mandatory")
    String username;
    @NotBlank(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    String password;
}
