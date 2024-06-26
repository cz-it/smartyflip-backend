/*
 *
 *  *   *******************************************************************
 *  *   *  Copyright (c) Author: Igor Volotovskyi ("Copyright "2024")2024.
 *  *   *******************************************************************
 *
 */

package smartyflip.modules.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(nullable = false, unique = true)
    private String tag;

    @ManyToMany(mappedBy = "tags")
    private Set<Module> modules = new HashSet<>();

//    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "tag_modules",
//            joinColumns = @JoinColumn(name = "tag_id"),
//            inverseJoinColumns = @JoinColumn(name = "module_id")
//    )
//    private Set<Module> modules = new HashSet<>();


    public Tag(String tag) {
        this.tag = tag;
    }
}
