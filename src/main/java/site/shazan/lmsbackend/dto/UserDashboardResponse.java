package site.shazan.lmsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDashboardResponse {
    private Long userId;
    private String username;
    private String profileImageUrl;
    private List<DashboardCourseResponse> enrolledCourses;
}

