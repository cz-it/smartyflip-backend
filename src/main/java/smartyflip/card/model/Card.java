/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.card.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import smartyflip.card.model.enums.Level;
import smartyflip.modules.model.Module;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "cardId")
@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardId")
    private Long cardId;

    @Column(name = "question", nullable = false, length = 500)
    private String question;

    @Column(name = "answer", nullable = false, length = 2500)
    private String answer;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private Level level;

    @CreatedDate
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(name = "moduleId", nullable = false)
    private Long moduleId;

    @Column(name = "moduleName")
    private String moduleName;

    @ManyToOne
    @JoinColumn(name = "module_module_id")
    Module module;
}
