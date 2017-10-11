package org.wso2.carbon.apimgt.impl;

import org.mockito.Mockito;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;

public class ResourceMockCreator {
    private Resource resource;

    public ResourceMockCreator(Object content) throws RegistryException {
        resource = Mockito.mock(Resource.class);
        Mockito.when(resource.getContent()).thenReturn(content);
    }

    Resource getMock() {
        return resource;
    }
}
