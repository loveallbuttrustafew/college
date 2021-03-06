package leon.college.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
@Data
@Builder
@EqualsAndHashCode(exclude = {"group"})
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @OneToOne
    @JoinColumn(name = "teacherId", nullable = false)
    private User teacher;
    @Column(nullable = false)
    private Boolean finished = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId", nullable = false)
    private Group group;

    @Tolerate
    Subject() {
    }
}
