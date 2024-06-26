package smartyflip.accounting.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import smartyflip.modules.model.Module;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@Builder
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "is_email_activated", nullable = false)
    private boolean isEmailActivated;

    @Column(name = "image_url", nullable = false)
    private String image;

    @Column(name = "date_of_registration")
    private LocalDate dateRegistered;

    @OneToMany(mappedBy = "userAccount", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Module> modules = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Singular
    private Set<Role> roles;


    public UserAccount() {
        this.isEmailActivated = false;
        this.image = "https://gravatar.com/avatar/0?d=retro";
        this.dateRegistered = LocalDate.now();
        this.roles = new HashSet<>();
    }

    public boolean addRole(Role role) {
        return roles.add(role);
    }

    public boolean removeRole(Role role) {
        return roles.remove(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
