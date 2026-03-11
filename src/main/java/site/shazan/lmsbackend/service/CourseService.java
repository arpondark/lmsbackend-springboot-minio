package site.shazan.lmsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.shazan.lmsbackend.dto.CourseRequest;
import site.shazan.lmsbackend.dto.CourseResponse;
import site.shazan.lmsbackend.exception.ResourceNotFoundException;
import site.shazan.lmsbackend.model.Course;
import site.shazan.lmsbackend.model.User;
import site.shazan.lmsbackend.repository.CourseRepository;
import site.shazan.lmsbackend.repository.ReviewRepository;
import site.shazan.lmsbackend.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + request.getInstructorId()));

        Course course = new Course();
        updateCourseFromRequest(course, request, instructor);

        Course savedCourse = courseRepository.save(course);
        return mapToResponse(savedCourse);
    }

    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return mapToResponse(course);
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + request.getInstructorId()));

        updateCourseFromRequest(course, request, instructor);
        Course updatedCourse = courseRepository.save(course);
        return mapToResponse(updatedCourse);
    }

    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    private void updateCourseFromRequest(Course course, CourseRequest request, User instructor) {
        course.setCourseName(request.getCourseName());
        course.setCourseDescription(request.getCourseDescription());
        course.setImageUrl(request.getImageUrl());
        course.setVideoUrl(request.getVideoUrl());
        course.setInstructor(instructor);
        course.setPrice(request.getPrice());
        course.setCategory(request.getCategory());
        course.setLevel(request.getLevel());
        course.setDuration(request.getDuration());
    }

    private CourseResponse mapToResponse(Course course) {
        int enrolledStudentsCount = course.getEnrolledStudents() != null ? course.getEnrolledStudents().size() : 0;
        int reviewsCount = (int) reviewRepository.countByCourseId(course.getId());
        Double averageRating = reviewRepository.findAverageRatingByCourseId(course.getId());

        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .courseDescription(course.getCourseDescription())
                .imageUrl(course.getImageUrl())
                .videoUrl(course.getVideoUrl())
                .instructorId(course.getInstructor() != null ? course.getInstructor().getId() : null)
                .instructorName(course.getInstructor() != null ? course.getInstructor().getUsername() : null)
                .price(course.getPrice())
                .category(course.getCategory())
                .level(course.getLevel())
                .duration(course.getDuration())
                .enrolledStudentsCount(enrolledStudentsCount)
                .reviewsCount(reviewsCount)
                .averageRating(averageRating != null ? averageRating : 0.0)
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();
    }
}
