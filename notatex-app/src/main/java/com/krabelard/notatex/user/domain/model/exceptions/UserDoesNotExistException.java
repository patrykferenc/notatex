package com.krabelard.notatex.user.domain.model.exceptions;

import com.krabelard.notatex.user.domain.model.value.Username;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(Username username) {
        super("User with username " + username + " does not exist");
    }
}
