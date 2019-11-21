package leon.college.services;

import leon.college.models.Role;
import leon.college.models.User;
import leon.college.repositories.UserRepository;
import leon.college.services.exceptions.PasswordsDontMatchException;
import leon.college.services.exceptions.UserAlreadyExistsException;
import leon.college.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findByRole(Role role) throws UserNotFoundException {
        List<User> users = userRepository.findByRole(role);
        if (users == null)
            throw new UserNotFoundException();
        return users;
    }

    public User findByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UserNotFoundException();
        return user;
    }

    public void addUser(User user) throws UserAlreadyExistsException {
        try {
            findByUsername(user.getUsername());
            throw new UserAlreadyExistsException();
        } catch (UserNotFoundException e) {
            userRepository.save(user);
        }
    }

    public void changePassword(String username, String oldPassword, String newPassword) throws UserNotFoundException, PasswordsDontMatchException {
        User user = findByUsername(username);
        if (!user.getPassword().equals(oldPassword))
            throw new PasswordsDontMatchException();
        // TODO change password
        user.setPassword(newPassword);
    }
}
