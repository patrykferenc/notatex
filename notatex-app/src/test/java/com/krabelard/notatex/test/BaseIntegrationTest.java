package com.krabelard.notatex.test;

import com.krabelard.notatex.NotatexApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {NotatexApplication.class})
public abstract class BaseIntegrationTest {

}
