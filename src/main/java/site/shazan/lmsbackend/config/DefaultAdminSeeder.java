package site.shazan.lmsbackend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import site.shazan.lmsbackend.model.Role;
import site.shazan.lmsbackend.model.User;
import site.shazan.lmsbackend.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultAdminSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.default-admin.enabled:true}")
    private boolean enabled;

    @Value("${app.seed.default-admin.username:admin}")
    private String username;

    @Value("${app.seed.default-admin.password:admin}")
    private String password;

    @Value("${app.seed.default-admin.email:admin@lms.local}")
    private String email;

    @Value("${app.seed.default-admin.phone-number:0000000000}")
    private String phoneNumber;

    @Value("${app.seed.default-admin.first-name:System}")
    private String firstName;

    @Value("${app.seed.default-admin.last-name:Admin}")
    private String lastName;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (!enabled) {
            log.info("Default admin seeding is disabled.");
            return;
        }

        boolean usernameExists = userRepository.existsByUsername(username);
        boolean emailExists = userRepository.existsByEmail(email);

        if (usernameExists || emailExists) {
            log.info("Skipping default admin seed. Existing user found for username '{}' or email '{}'.", username, email);
            return;
        }

        User admin = new User();
        admin.setUsername(username);
        admin.setEmail(email);
        admin.setPhoneNumber(phoneNumber);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setFirstName(StringUtils.hasText(firstName) ? firstName : null);
        admin.setLastName(StringUtils.hasText(lastName) ? lastName : null);
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);
        log.warn("Seeded default admin user '{}' with default password. Change this password immediately.", username);
    }
}



