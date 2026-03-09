package site.shazan.lmsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import site.shazan.lmsbackend.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
