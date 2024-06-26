/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DatePeriodDto {

    @Valid
    @NotNull(message = "dateFrom is mandatory")
    // TODO validate date
    LocalDate dateFrom;

    @NotNull(message = "dateTo is mandatory")
    // TODO validate date
    LocalDate dateTo;
}
