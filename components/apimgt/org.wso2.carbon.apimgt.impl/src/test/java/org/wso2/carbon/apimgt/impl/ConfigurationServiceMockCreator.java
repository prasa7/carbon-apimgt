package org.wso2.carbon.apimgt.impl;

import org.mockito.Mockito;

public class ConfigurationServiceMockCreator {
    private ConfigurationMockCreator configurationMockCreator = new ConfigurationMockCreator();
    APIManagerConfigurationService configurationService;

    ConfigurationServiceMockCreator() {
        configurationService = Mockito.mock(APIManagerConfigurationService.class);
        Mockito.when(configurationService.getAPIManagerConfiguration()).thenReturn(configurationMockCreator.getMock());
    }

    APIManagerConfigurationService getMock() {
        return configurationService;
    }
}
