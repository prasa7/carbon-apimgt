package org.wso2.carbon.apimgt.impl;

import org.mockito.Mockito;
import org.wso2.carbon.apimgt.impl.dto.ThrottleProperties;

public class ConfigurationMockCreator {
    private ThrottleProperties throttleProperties = new ThrottleProperties();
    private APIManagerConfiguration configuration;

    ConfigurationMockCreator() {
        throttleProperties.setEnabled(true);
        throttleProperties.setEnableUnlimitedTier(true);
        configuration = Mockito.mock(APIManagerConfiguration.class);
        Mockito.when(configuration.getThrottleProperties()).thenReturn(throttleProperties);
    }

    APIManagerConfiguration getMock() {
        return configuration;
    }
}
