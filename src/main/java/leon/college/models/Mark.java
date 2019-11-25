package leon.college.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "marks")
@Data
@Builder
@EqualsAndHashCode
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime date;
    @OneToOne
    @JoinColumn(name = "subjectId", nullable = false)
    private Subject subject;
    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    @Column(nullable = false)
    private Byte mark;

    @Tolerate
    Mark() {
    }
}
