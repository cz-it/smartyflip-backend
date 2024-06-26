/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smartyflip.card.dto.CardDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ModuleDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long moduleId;

    String moduleName;

    @JsonProperty("username")
    String userName;

    LocalDateTime dateCreated;

    String stackName;

    @Builder.Default
    Set<CardDto> cards = new HashSet<>();
}
