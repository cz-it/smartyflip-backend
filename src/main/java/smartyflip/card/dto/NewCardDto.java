/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewCardDto {

    @NotNull(message = "Question is mandatory")
    @NotBlank(message = "Question is mandatory")
    @Size(min = 5, max = 2500, message = "Question must be between 5 and 2500 characters")
    String question;

    @NotNull(message = "Answer is mandatory")
    @NotBlank(message = "Answer is mandatory")
    @Size(min = 5, max = 2500, message = "Answer must be between 5 and 2500 characters")
    String answer;

    @NotNull(message = "Level is mandatory")
    @NotBlank(message = "Level is mandatory")
    @Pattern(regexp = "(?i)EASY|MEDIUM|HARD", message = "Level must be one of the following: EASY, MEDIUM, HARD")
    String level;

    @NotNull(message = "Module id is mandatory")
    Long moduleId;
}
