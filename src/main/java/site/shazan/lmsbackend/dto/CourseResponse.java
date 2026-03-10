package site.shazan.lmsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String courseName;
    private String courseDescription;
    private String imageUrl;
    private String videoUrl;
    private Long instructorId;
    private String instructorName;
    private BigDecimal price;
    private String category;
    private String level;
    private Integer duration;
    private Integer enrolledStudentsCount;
    private Integer reviewsCount;
    private Double averageRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

