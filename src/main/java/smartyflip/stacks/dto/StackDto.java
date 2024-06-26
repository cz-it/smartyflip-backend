/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.dto;

import lombok.*;
import smartyflip.modules.dto.ModuleDto;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "stackName")
@Builder
public class StackDto {

    Long stackId;

    String stackName;

    @Builder.Default
    Set<ModuleDto> modules = new HashSet<>();
}
