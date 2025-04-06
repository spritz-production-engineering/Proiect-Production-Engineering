package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.User;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUseri() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User createUser(User userDTO) {
        UserEntity entity = new UserEntity(
                userDTO.getId(),
                userDTO.getNume(),
                userDTO.getPrenume(),
                userDTO.getEmail()
        );
        UserEntity saved = userRepository.save(entity);
        return new User(
                saved.getId(),
                saved.getNume(),
                saved.getPrenume(),
                saved.getEmail()
        );
    }

    public Optional<UserEntity> updateUser(String id, UserEntity userDetails) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setNume(userDetails.getNume());
            existingUser.setPrenume(userDetails.getPrenume());
            existingUser.setEmail(userDetails.getEmail());
            return userRepository.save(existingUser);
        });
    }

    public void deleteUser(String id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
