package leon.college.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private Boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;
}
