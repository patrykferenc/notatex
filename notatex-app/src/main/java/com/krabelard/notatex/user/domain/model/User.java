package com.krabelard.notatex.user.domain.model;

import com.krabelard.notatex.user.domain.model.value.HashedPassword;
import com.krabelard.notatex.user.domain.model.value.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User {

    private final Long id;
    private final Username username;
    private final HashedPassword password;
}
