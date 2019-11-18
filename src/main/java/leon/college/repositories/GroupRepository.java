package leon.college.repositories;

import leon.college.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByTitleAndCourseAndNumberAndCommercial(String title, Byte course, Byte number, Boolean commercail);
}
