package leon.college.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "subjects")
@Data
@Builder
@ToString
@EqualsAndHashCode(exclude = {"group"})
public class Subject {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Group group;

    @Tolerate
    Subject(){}
}
