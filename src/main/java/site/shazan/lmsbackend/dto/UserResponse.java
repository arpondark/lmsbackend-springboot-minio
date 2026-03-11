package site.shazan.lmsbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.shazan.lmsbackend.model.Role;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Role role = Role.USER;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer enrolledCoursesCount;
    private Integer createdCoursesCount;
}
