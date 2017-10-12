package org.wso2.carbon.apimgt.hostobjects;

import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.engine.AxisConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.wso2.carbon.apimgt.hostobjects.internal.HostObjectComponent;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.APIManagerConfiguration;
import org.wso2.carbon.apimgt.impl.utils.APIUtil;
import org.wso2.carbon.apimgt.keymgt.client.SubscriberKeyMgtClient;
import org.wso2.carbon.utils.ConfigurationContextService;


@RunWith(PowerMockRunner.class)
@PrepareForTest({HostObjectComponent.class, APIUtil.class})
public class HostObjectUtilsTest {
    private HostObjectUtils hostObject = new HostObjectUtils();
    @Test
    public void setConfigContextService() throws Exception {
    }

    @Test
    public void getConfigContext() throws Exception {
    }

    @Test
    public void getBackendPort() throws Exception {
        //success case

        AxisConfiguration axisConfiguration = new AxisConfiguration();
        ConfigurationContext context = Mockito.mock(ConfigurationContext.class);
        ConfigurationContextService contextService = Mockito.mock(ConfigurationContextService.class);
        Mockito.when(contextService.getServerConfigContext()).thenReturn(context);
        Mockito.when(context.getAxisConfiguration()).thenReturn(axisConfiguration);
        hostObject.setConfigContextService(contextService);
        String returnedPortSuccess = hostObject.getBackendPort("http");
        Assert.assertNotNull(returnedPortSuccess);
        Assert.assertNotEquals("", returnedPortSuccess);

        //error case
        hostObject.setConfigContextService(null);
        String returnedPortError = hostObject.getBackendPort("http");
        Assert.assertNull(returnedPortError);

    }

    @Test
    public void getKeyManagementClient() throws Exception {
        PowerMockito.mockStatic(HostObjectComponent.class);
        HostObjectComponent hostObjectComponent = Mockito.mock(HostObjectComponent.class);
        APIManagerConfiguration apimConfiguration = Mockito.mock(APIManagerConfiguration.class);
        Mockito.when(hostObjectComponent.getAPIManagerConfiguration()).thenReturn(apimConfiguration);
        Mockito.when(apimConfiguration.getFirstProperty(APIConstants.API_KEY_VALIDATOR_URL)).thenReturn("https://localhost:9443/services/");
        Mockito.when(apimConfiguration.getFirstProperty(APIConstants.API_KEY_VALIDATOR_USERNAME)).thenReturn("admin");
        Mockito.when(apimConfiguration.getFirstProperty(APIConstants.API_KEY_VALIDATOR_PASSWORD)).thenReturn("admin");

        Assert.assertTrue(hostObject.getKeyManagementClient() instanceof SubscriberKeyMgtClient);
    }

    @Test
    public void getProviderClient() throws Exception {


    }

    @Test
    public void checkDataPublishingEnabled() throws Exception {

    }

    @Test
    public void invalidateRecentlyAddedAPICache() throws Exception {

    }

    @Test
    public void isUsageDataSourceSpecified() throws Exception {
    }

    @Test
    public void isStatPublishingEnabled() throws Exception {
    }

    @Test
    public void sendHttpHEADRequest() throws Exception {
    }

}