package smartyflip.accounting.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="email_activation_keys")
@Getter
@Setter
@NoArgsConstructor
public class EmailActivationKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private Integer userId;

    public EmailActivationKey(Integer userId) {
        this.userId = userId;
    }
}
