package leon.college.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "information")
@Data
@Builder
@EqualsAndHashCode
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String filePath;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Tolerate
    Information() {
    }
}
