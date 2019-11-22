package leon.college.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
@Data
@Builder
@ToString
@EqualsAndHashCode(exclude = {"users", "subjects"})
public class Group {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Boolean commercial;
    @Column(nullable = false)
    private Byte course;
    @Column(nullable = false)
    private Byte number;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<Subject> subjects = new HashSet<>();

    @Tolerate
    Group() {
    }
}
