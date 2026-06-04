package com.gogil.sellercrm.domain.user;

import java.util.Optional;

public interface IUserRepository {

    User save(User user);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
