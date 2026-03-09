package site.shazan.lmsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.shazan.lmsbackend.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
}
