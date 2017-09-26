/*
*  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.apimgt.gateway.mediators;

import junit.framework.TestCase;
import org.apache.synapse.MessageContext;
import org.junit.Assert;
import org.wso2.carbon.apimgt.gateway.TestUtils;
import org.wso2.carbon.apimgt.gateway.handlers.security.APISecurityUtils;
import org.wso2.carbon.apimgt.gateway.handlers.security.AuthenticationContext;

/**
 * TokenParser test case to test setting headers to auth context
 */
public class TokenParserMediatorTest extends TestCase {

    TokenPasser mediator;

    /**
     * test Mediate through TokenParser handler.
     * @throws Exception when mediation failed or caller toke different.
     */
    public void testMediate() throws Exception {
        MessageContext messageContext = TestUtils.getMessageContextWithAuthContext("test", "1.0.0");
        mediator = new TokenPasser();
        boolean isMediated = mediator.mediate(messageContext);
        assertTrue(isMediated);
        AuthenticationContext authContext = APISecurityUtils.getAuthenticationContext(messageContext);
        Assert.assertEquals(authContext.getCallerToken(), "987654321");
    }
}