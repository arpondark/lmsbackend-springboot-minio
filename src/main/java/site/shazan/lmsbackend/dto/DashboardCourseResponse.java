package site.shazan.lmsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCourseResponse {
    private Long id;
    private String courseName;
    private String imageUrl;
    private String videoUrl;
    private String instructorName;
}

