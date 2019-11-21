package leon.college.models;

import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "persons")
@Data
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Group group;

    @Tolerate
    User(){}
}
