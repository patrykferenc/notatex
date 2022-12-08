package com.krabelard.notatex.user.domain.model.value;

import lombok.Value;

@Value(staticConstructor = "of")
public class Username {

    String value;
}
