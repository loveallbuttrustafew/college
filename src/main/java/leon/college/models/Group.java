package leon.college.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "groups")
@Data
@EqualsAndHashCode
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
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<User> users;
}
