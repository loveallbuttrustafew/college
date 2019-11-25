package leon.college.services;

import leon.college.models.Subject;
import leon.college.repositories.SubjectRepository;
import leon.college.services.exceptions.SubjectAlreadyExists;
import leon.college.services.exceptions.SubjectDoesntFoundedException;
import leon.college.services.exceptions.SubjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public Subject findById(Long id) throws SubjectNotFoundException {
        Optional<Subject> subject = subjectRepository.findById(id);
        if (!subject.isPresent())
            throw new SubjectNotFoundException();
        return subject.get();
    }

    public Subject findBySubject(Subject subject) throws SubjectDoesntFoundedException {
        Optional<Subject> findedSubject = subjectRepository.findByTitleAndGroup(subject.getTitle(), subject.getGroup());
        if (!findedSubject.isPresent())
            throw new SubjectDoesntFoundedException();
        return findedSubject.get();
    }

    public void addSubject(Subject subject) throws SubjectAlreadyExists {
        try {
            findBySubject(subject);
            throw new SubjectAlreadyExists();
        } catch (SubjectDoesntFoundedException e) {
            subjectRepository.save(subject);
        }
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }
}
