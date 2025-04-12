package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private User convertToDto(UserEntity entity) {
        return new User(entity.getId(), entity.getNume(), entity.getPrenume(), entity.getEmail());
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUseri()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) throws EntityNotFoundException {
        return userService.getUserById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found."));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) throws EntityNotFoundException {
        UserEntity userEntity = new UserEntity(
                user.getId(),
                user.getNume(),
                user.getPrenume(),
                user.getEmail()
        );

        Optional<UserEntity> updated = userService.updateUser(id, userEntity);
        return updated.map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found."));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) throws EntityNotFoundException {
        userService.deleteUser(id);
    }
}
