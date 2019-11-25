package leon.college.repositories;

import leon.college.models.Group;
import leon.college.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByTitleAndGroup(String title, Group group);
}
