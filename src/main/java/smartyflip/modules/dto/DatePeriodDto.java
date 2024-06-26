/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class DatePeriodDto {

    @Valid
    @NotNull(message = "dateFrom is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "dateFrom is mandatory")
    @PastOrPresent(message = "dateFrom should be in the past or present")
    LocalDate dateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "dateTo is mandatory")
    @FutureOrPresent(message = "dateTo should be in the future or present")
    LocalDate dateTo;
}
