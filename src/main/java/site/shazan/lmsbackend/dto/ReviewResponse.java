package site.shazan.lmsbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long courseId;
    private String courseName;
    private String reviewText;
    private Integer rating;
    private LocalDateTime createdAt;
}

