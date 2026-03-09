package site.shazan.lmsbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String password;

    private Role role= Role.USER;
}
