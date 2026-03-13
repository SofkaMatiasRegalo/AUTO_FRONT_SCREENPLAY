package com.autofrontscreenplay.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/autenticacion")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,            value = "pretty, io.cucumber.core.plugin.SerenityReporterParallel")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,              value = "com.autofrontscreenplay.stepdefinitions")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME,       value = "not @wip and not @manual")
@ConfigurationParameter(key = SNIPPET_TYPE_PROPERTY_NAME,      value = "camelcase")
public class AutenticacionTestRunner {
}
