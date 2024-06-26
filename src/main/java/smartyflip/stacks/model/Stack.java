/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.stacks.model;

import jakarta.persistence.*;
import lombok.*;
import smartyflip.modules.model.Module;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "stackId")
@Entity
@Table(name = "stack")
public class Stack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stack_id")
    private Long stackId;

    @Column(name = "stackName", nullable = false, unique = true)
    private String stackName;

    @OneToMany(mappedBy = "stack", cascade = CascadeType.ALL, orphanRemoval = true)
    @Singular
    private Set<Module> modules;

    public Stack() {
        this.modules = new HashSet<>();
    }

    public int getModulesAmount() {
        return modules.size();
    }

}