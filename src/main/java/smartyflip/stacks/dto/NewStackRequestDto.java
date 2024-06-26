package smartyflip.stacks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewStackRequestDto {
    @NotNull(message = "Stack name is mandatory")
    @NotBlank(message = "Stack name is mandatory")
    String stackName;
}
