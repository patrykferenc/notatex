package com.krabelard.notatex.user.domain.model.exceptions;

import com.krabelard.notatex.user.domain.model.User;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(User user) {
        super("User with username " + user.getUsername() + " already exists");
    }
}
