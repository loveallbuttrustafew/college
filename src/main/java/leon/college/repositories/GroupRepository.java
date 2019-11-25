package leon.college.repositories;

import leon.college.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByTitleAndCourseAndNumberAndCommercial(String title, Byte course, Byte number, Boolean commercail);
}
