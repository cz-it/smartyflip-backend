/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.service.util;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smartyflip.modules.model.Module;

@Setter // FIXME @Setter set for the testing purpose
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@DiscriminatorValue("REGISTERED")
public class RegisteredModule extends Module {

    private String subscriptionType;    // Type of subscription

    // RESERVED OPTIONS
//    @Getter
//    private LocalDate accessEndDate;    // Date when the access ends
//
//    private boolean hasAdditionalContent;  // Indicates if additional features/content are available
//
//    public boolean hasAdditionalContent() {
//        return hasAdditionalContent;
//    }

}
