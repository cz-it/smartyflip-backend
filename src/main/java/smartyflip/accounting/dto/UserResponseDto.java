package smartyflip.accounting.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    Integer id;
    String username;
    String firstName;
    String lastName;
    String email;
    @JsonProperty("isEmailConfirmed")
    boolean emailActivated;
    String image;
    LocalDate dateRegistered;
    Set<String> roles;
}
