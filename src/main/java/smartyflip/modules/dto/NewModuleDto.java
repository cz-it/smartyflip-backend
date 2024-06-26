/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewModuleDto {
    @Valid
    @NotNull(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    String username;

    @NotNull(message = "moduleName is mandatory")
    @NotBlank(message = "moduleName must be greater than 2 characters")
    @Size(min = 2, max = 200, message = "moduleName must be greater than 2 characters")
    String moduleName;
    @NotNull(message = "stackName is mandatory")
    @NotBlank(message = "stackName is mandatory")
    String stackName;

}
