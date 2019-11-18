package leon.college.services;

import leon.college.models.Group;
import leon.college.repositories.GroupRepository;
import leon.college.services.exceptions.GroupAlreadyExists;
import leon.college.services.exceptions.GroupNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public Group findByGroupId(Long groupId) throws GroupNotFoundException {
        Optional<Group> group = groupRepository.findById(groupId);

        if (!group.isPresent()) {
            throw new GroupNotFoundException();
        }

        return group.get();
    }

    public List<Group> findAll() throws GroupNotFoundException {
        List<Group> groups = groupRepository.findAll();

        if (groups == null)
            throw new GroupNotFoundException();

        return groups;
    }

    public Group findByGroup(Group group) throws GroupNotFoundException {
        Group findedGroup = groupRepository.findByTitleAndCourseAndNumberAndCommercial(
                group.getTitle(), group.getCourse(), group.getNumber(), group.getCommercial());
        if (findedGroup == null) {
            throw new GroupNotFoundException();
        }
        return findedGroup;
    }

    public void addGroup(Group group) throws GroupAlreadyExists {
        try {
            findByGroup(group);
            throw new GroupAlreadyExists();
        } catch (GroupNotFoundException e) {
            groupRepository.save(group);
        }
    }
}
