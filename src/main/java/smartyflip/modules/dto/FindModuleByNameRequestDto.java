package smartyflip.modules.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FindModuleByNameRequestDto {
    @Valid
    @NotNull(message = "Search query must not be empty")
    @NotBlank(message = "Search query must not be empty")
    String name;
}
