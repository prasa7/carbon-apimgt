package org.wso2.carbon.apimgt.impl;

import org.mockito.Mockito;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.core.service.RealmService;

public class RealmServiceMockCreator {
    private RealmService realmService;
    private TenantManagerMockCreator tenantManagerMockCreator;

    public RealmServiceMockCreator(int tenantId) throws UserStoreException {
        tenantManagerMockCreator = new TenantManagerMockCreator(tenantId);
        realmService = Mockito.mock(RealmService.class);
        Mockito.when(realmService.getTenantManager()).thenReturn(tenantManagerMockCreator.getMock());
    }

    RealmService getMock() {
        return realmService;
    }

}
