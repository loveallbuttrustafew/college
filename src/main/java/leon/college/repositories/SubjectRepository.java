package leon.college.repositories;

import leon.college.models.Group;
import leon.college.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByTitleAndGroup(String title, Group group);
}
