package com.bbse.identity;

import com.bbse.identity.model.Role;
import com.bbse.identity.model.User;
import com.bbse.identity.repository.RoleRepository;
import com.bbse.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class InitDataLoader implements CommandLineRunner { // alternative: ApplicationRunner
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepository.findById(Role.ADMIN).isEmpty()) {
            Role adminRole = Role.builder().name(Role.ADMIN).description("Admin").build();
            roleRepository.save(adminRole);
        }

        if (roleRepository.findById(Role.USER).isEmpty()) {
            Role userRole = Role.builder().name(Role.USER).description("User").build();
            roleRepository.save(userRole);
        }

        if (userRepository.findByEmail("admin@bbse.com").isEmpty()) {
            Role adminRole = roleRepository.findById(Role.ADMIN).orElseThrow();
            User user = User.builder()
                    .name("Admin")
                    .email("admin@bbse.com")
                    .password(passwordEncoder.encode("password"))
                    .role(adminRole)
                    .build();
            userRepository.save(user);
        }
    }

}
