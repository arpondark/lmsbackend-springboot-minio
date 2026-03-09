package site.shazan.lmsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.shazan.lmsbackend.model.Review;
@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
}
