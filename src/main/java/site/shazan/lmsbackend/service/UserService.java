package site.shazan.lmsbackend.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.shazan.lmsbackend.dto.AdminUserRequest;
import site.shazan.lmsbackend.dto.UserRequest;
import site.shazan.lmsbackend.dto.UserResponse;
import site.shazan.lmsbackend.exception.ResourceNotFoundException;
import site.shazan.lmsbackend.model.Role;
import site.shazan.lmsbackend.model.User;
import site.shazan.lmsbackend.repository.UserRepository;

import java.util.Arrays;
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
                .phoneNumber(user.getPhoneNumber())
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
        validateUniqueUserFields(userRequest.getUsername(), userRequest.getEmail());

        User user = new User();
        updateUserFromRequest(user, userRequest, true);
        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    @Transactional
    public UserResponse addUserAsAdmin(AdminUserRequest userRequest) {
        validateUniqueUserFields(userRequest.getUsername(), userRequest.getEmail());

        User user = new User();
        updateUserFromAdminRequest(user, userRequest);
        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    private void validateUniqueUserFields(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    private void updateUserFromRequest(User user, UserRequest userRequest, boolean applyDefaultRole) {
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setPassword(userRequest.getPassword()); // TODO: Add password encoding with BCrypt
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        if (!applyDefaultRole && userRequest.getRole() != null && !userRequest.getRole().isBlank()) {
            user.setRole(parseRole(userRequest.getRole()));
        } else if (applyDefaultRole) {
            user.setRole(Role.USER);
        }
    }

    private void updateUserFromAdminRequest(User user, AdminUserRequest userRequest) {
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setPassword(userRequest.getPassword()); // TODO: Add password encoding with BCrypt
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setRole(parseRole(userRequest.getRole()));
    }

    private Role parseRole(String roleValue) {
        try {
            return Role.valueOf(roleValue.toUpperCase());
        } catch (IllegalArgumentException ex) {
            String allowedRoles = Arrays.stream(Role.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException("Invalid role. Allowed roles: " + allowedRoles);
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

        if (!user.getUsername().equals(userRequest.getUsername())
                && userRepository.existsByUsername(userRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (!user.getEmail().equals(userRequest.getEmail())
                && userRepository.existsByEmail(userRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        updateUserFromRequest(user, userRequest, false);

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
