package site.shazan.lmsbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String courseId;
    private String courseName;
    private String courseDescription;
    private String imageUrl;
    private String instructorName;
    private String videoUrl;
    private List<Review> reviews;

}
