package org.wso2.carbon.apimgt.impl;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.wso2.carbon.apimgt.impl.internal.ServiceReferenceHolder;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.user.api.UserStoreException;

public class ServiceReferenceHolderMockCreator {
    private ConfigurationServiceMockCreator configurationServiceMockCreator = new ConfigurationServiceMockCreator();
    private ServiceReferenceHolder serviceReferenceHolder;
    private RealmServiceMockCreator realmServiceMockCreator;
    private RegistryServiceMockCreator registryServiceMockCreator;

    ServiceReferenceHolderMockCreator(int tenantId) throws RegistryException, UserStoreException {
        serviceReferenceHolder = Mockito.mock(ServiceReferenceHolder.class);
        realmServiceMockCreator = new RealmServiceMockCreator(tenantId);

        Mockito.when(serviceReferenceHolder.getAPIManagerConfigurationService()).
                thenReturn(configurationServiceMockCreator.getMock());
        Mockito.when(serviceReferenceHolder.getRealmService()).thenReturn(realmServiceMockCreator.getMock());


        PowerMockito.mockStatic(ServiceReferenceHolder.class);
        Mockito.when(ServiceReferenceHolder.getInstance()).thenReturn(serviceReferenceHolder);
    }

    ServiceReferenceHolder getMock() {
        return serviceReferenceHolder;
    }

    public void initRegistryServiceMockCreator(boolean isResourceExists, Object content) throws RegistryException {
        registryServiceMockCreator = new RegistryServiceMockCreator(isResourceExists, content);
        Mockito.when(serviceReferenceHolder.getRegistryService()).thenReturn(registryServiceMockCreator.getMock());
    }

}
