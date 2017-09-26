package org.wso2.carbon.apimgt.gateway.handlers.security;

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

import org.apache.axis2.engine.AxisConfiguration;
import org.apache.synapse.MessageContext;
import org.apache.synapse.config.SynapseConfiguration;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2SynapseEnvironment;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.wso2.carbon.apimgt.gateway.handlers.security.oauth.OAuthAuthenticator;
import org.wso2.carbon.apimgt.impl.APIManagerConfigurationService;
import org.wso2.carbon.metrics.manager.Timer;

public class APIAuthenticationhandlerTestCase {


    @Test
    public void handleRequestTest() {
        SynapseEnvironment synapseEnvironment = Mockito.mock(SynapseEnvironment.class);
        MessageContext messageContext = Mockito.mock(MessageContext.class);
        APIAuthenticationHandler apiAuthenticationHandler = createAPIAuthenticationHandler();
        apiAuthenticationHandler.init(synapseEnvironment);
        Assert.assertTrue(apiAuthenticationHandler.handleRequest(messageContext));
    }

    private APIAuthenticationHandler createAPIAuthenticationHandler() {
        return new APIAuthenticationHandler() {

            @Override
            protected APIManagerConfigurationService getApiManagerConfigurationService() {
                return Mockito.mock(APIManagerConfigurationService.class);
            }

            @Override
            protected boolean isAnalyticsEnabled() {
                return true;
            }

            @Override
            protected Authenticator getAuthenticator() {
                return new OAuthAuthenticator() {
                    @Override
                    public void init(SynapseEnvironment env) {
                    }

                    @Override
                    public boolean authenticate(MessageContext synCtx) throws APISecurityException {
                        return true;
                    }
                };
            }

            @Override
            protected void initializeAuthenticator() {
                getAuthenticator().init(null);
            }

            @Override
            protected boolean isAuthenticate(MessageContext messageContext) throws APISecurityException {
                return true;
            }

            @Override
            protected void setAPIParametersToMessageContext(MessageContext messageContext) {

            }

            @Override
            protected void stopMetricTimer(Timer.Context context) {

            }

            @Override
            protected Timer.Context startMetricstimer() {
                return null;
            }
        };
    }

    private SynapseEnvironment createSynapseEnvironment() {
        return new Axis2SynapseEnvironment(new SynapseConfiguration()) {
            @Override
            public SynapseConfiguration getSynapseConfiguration() {
                return new SynapseConfiguration() {
                    @Override
                    public AxisConfiguration getAxisConfiguration() {
                        return new AxisConfiguration();
                    }
                };
            }
        };

    }
}

