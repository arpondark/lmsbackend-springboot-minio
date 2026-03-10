package site.shazan.lmsbackend.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.shazan.lmsbackend.dto.UserRequest;
import site.shazan.lmsbackend.dto.UserResponse;
import site.shazan.lmsbackend.exception.ResourceNotFoundException;
import site.shazan.lmsbackend.model.Role;
import site.shazan.lmsbackend.model.User;
import site.shazan.lmsbackend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .enrolledCoursesCount(user.getEnrolledCourses() != null ? user.getEnrolledCourses().size() : 0)
                .createdCoursesCount(user.getCreatedCourses() != null ? user.getCreatedCourses().size() : 0)
                .build();
    }

    @Transactional
    public UserResponse addUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        updateUserFromRequest(user, userRequest);
        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword()); // TODO: Add password encoding with BCrypt
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        // Set role from request or default to USER
        if (userRequest.getRole() != null) {
            try {
                user.setRole(Role.valueOf(userRequest.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                user.setRole(Role.USER);
            }
        } else {
            user.setRole(Role.USER);
        }
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToUserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Update only allowed fields (username and email should be checked for uniqueness)
        if (!user.getEmail().equals(userRequest.getEmail())
                && userRepository.existsByEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(userRequest.getPassword()); // TODO: Add password encoding
        }

        User updatedUser = userRepository.save(user);
        return mapToUserResponse(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return mapToUserResponse(user);
    }
}
