package com.krabelard.notatex.user.domain.model;

import com.krabelard.notatex.user.domain.model.exceptions.UserAlreadyExistsException;
import com.krabelard.notatex.user.domain.model.exceptions.UserDoesNotExistException;
import com.krabelard.notatex.user.domain.model.value.Username;
import com.krabelard.notatex.user.domain.ports.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    public User getUserByUsername(Username username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserDoesNotExistException(username));
    }

    // save user if not in the database and throw exception if user already exists
    public void saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(user);
        }
        userRepository.saveUser(user);
    }

}
