package leon.college.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "finishedLaboratory")
@Data
@Builder
@EqualsAndHashCode
public class FinishedLaboratory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "laboratoryId", nullable = false)
    private Laboratory laboratory;
    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    @Column(nullable = false)
    private String filePath;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LaboratoryStatus status = LaboratoryStatus.NOTSEEN;

    @Tolerate
    FinishedLaboratory() {
    }
}
