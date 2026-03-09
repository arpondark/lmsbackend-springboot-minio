package site.shazan.lmsbackend.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.shazan.lmsbackend.dto.UserRequest;
import site.shazan.lmsbackend.dto.UserResponse;
import site.shazan.lmsbackend.model.User;
import site.shazan.lmsbackend.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    private Object mapToUserResponse(User user) {
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
    }


    public Optional<UserResponse> getUserById(String id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }
}
