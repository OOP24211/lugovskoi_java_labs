package com.gogil.sellercrm.config;

import com.gogil.sellercrm.domain.user.IUserRepository;
import com.gogil.sellercrm.domain.user.Role;
import com.gogil.sellercrm.domain.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin", passwordEncoder.encode("admin"), Role.ADMIN, null);
            userRepository.save(admin);
        }
    }
}
