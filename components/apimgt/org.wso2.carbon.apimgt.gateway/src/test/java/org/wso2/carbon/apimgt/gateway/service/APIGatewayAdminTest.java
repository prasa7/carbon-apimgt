/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.apimgt.gateway.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.wso2.carbon.apimgt.gateway.utils.RESTAPIAdminClient;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = APIGatewayAdmin.class)
public class APIGatewayAdminTest {
    @Test
    public void testAddApi() throws Exception {
        String apiConfig = "aa";
        String tenantDomain = "carbon.super";
        RESTAPIAdminClient restapiAdminClient = Mockito.mock(RESTAPIAdminClient.class);
        PowerMockito.whenNew(RESTAPIAdminClient.class).withArguments(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString()).thenReturn(restapiAdminClient);
        Mockito.when(restapiAdminClient.addApi(apiConfig, tenantDomain)).thenReturn(true);
        Mockito.when(restapiAdminClient.addApi(apiConfig)).thenReturn(true);
        Mockito.when(restapiAdminClient.addPrototypeApiScriptImpl(apiConfig, tenantDomain)).thenReturn(true);
        Mockito.when(restapiAdminClient.addPrototypeApiScriptImpl(apiConfig)).thenReturn(true);
        APIGatewayAdmin apiGatewayAdmin = new APIGatewayAdmin();
        Assert.assertTrue(apiGatewayAdmin.addApiForTenant("admin", "API1", "1.0.0", apiConfig, tenantDomain));
        Assert.assertTrue(apiGatewayAdmin.addApi("admin", "API1", "1.0.0", apiConfig));
        Assert.assertTrue(apiGatewayAdmin.addPrototypeApiScriptImplForTenant("admin", "API1", "1.0.0", apiConfig,
                tenantDomain));
        Assert.assertTrue(apiGatewayAdmin.addPrototypeApiScriptImpl("admin", "API1", "1.0.0", apiConfig));
    }
}
