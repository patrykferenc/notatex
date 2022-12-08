package com.krabelard.notatex.user.domain.ports;

import com.krabelard.notatex.user.domain.model.User;
import com.krabelard.notatex.user.domain.model.value.Username;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(Username username);

    void saveUser(User user);
}
