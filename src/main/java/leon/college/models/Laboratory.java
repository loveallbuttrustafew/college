package leon.college.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "laboratories")
@Data
@Builder
@EqualsAndHashCode
public class Laboratory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "subjectId", nullable = false)
    private Subject subject;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String filePath;

    @Tolerate
    Laboratory() {
    }
}
