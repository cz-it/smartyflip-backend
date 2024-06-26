/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class PayloadRequiredException extends RuntimeException {
}
