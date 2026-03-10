package site.shazan.lmsbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {
    @NotBlank(message = "Course name is required")
    private String courseName;

    private String courseDescription;
    private String imageUrl;
    private String videoUrl;

    @NotNull(message = "Instructor ID is required")
    private Long instructorId;

    private BigDecimal price;
    private String category;
    private String level;
    private Integer duration;
}

