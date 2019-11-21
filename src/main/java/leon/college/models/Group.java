package leon.college.models;

import lombok.*;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "groups")
@Data
@Builder
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
    @Fetch(value=FetchMode.SELECT)
    private Set<User> users;

    @Tolerate
    Group(){}
}
