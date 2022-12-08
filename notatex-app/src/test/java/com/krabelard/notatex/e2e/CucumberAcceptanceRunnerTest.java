package com.krabelard.notatex.e2e;


import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("scenarios/acceptance")
public class CucumberAcceptanceRunnerTest {
}
