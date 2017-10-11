/*
 *   Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.wso2.carbon.apimgt.gateway.utils;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test class for SequenceAdminServiceClient
 */
public class SequenceAdminServiceClientTestCase {
    OMElement omElement = Mockito.mock(OMElement.class);

    @Test
    public void testAddSequence() {
        SequenceAdminServiceClient sequenceAdminServiceClient = null;
        try {
            sequenceAdminServiceClient = new SequenceAdminServiceClient();
        } catch (AxisFault axisFault) {
            // test for axisFault
        }
        try {
            sequenceAdminServiceClient.addSequence(omElement);
        } catch (AxisFault axisFault) {
            // test for axisFault
        }

        try {
            sequenceAdminServiceClient.addSequenceForTenant(omElement, "abc.com");
        } catch (AxisFault axisFault) {
            // test for axisFault
        }
    }

    @Test
    public void testDeleteSequence() {
        SequenceAdminServiceClient sequenceAdminServiceClient = null;
        try {
            sequenceAdminServiceClient = new SequenceAdminServiceClient();
        } catch (AxisFault axisFault) {
            // test for axisFault
        }
        try {
            sequenceAdminServiceClient.deleteSequence("xyz");
        } catch (AxisFault axisFault) {
            // test for axisFault
        }

        try {
            sequenceAdminServiceClient.deleteSequenceForTenant("xyz", "abc.com");
        } catch (AxisFault axisFault) {
            // test for axisFault
        }
    }

    @Test
    public void testGetSequence() {
        SequenceAdminServiceClient sequenceAdminServiceClient = null;
        try {
            sequenceAdminServiceClient = new SequenceAdminServiceClient();
        } catch (AxisFault axisFault) {
            // test for axisFault
        }
        try {
            sequenceAdminServiceClient.getSequence("xyz");
        } catch (AxisFault axisFault) {
            // test for axisFault
        }

        try {
            sequenceAdminServiceClient.getSequenceForTenant("xyz", "abc.com");
        } catch (AxisFault axisFault) {
            // test for axisFault
        }
    }

    @Test
    public void testIsExsistingSequence() {
        SequenceAdminServiceClient sequenceAdminServiceClient = null;
        try {
            sequenceAdminServiceClient = new SequenceAdminServiceClient();
        } catch (AxisFault axisFault) {
            // test for axisFault
        }
        try {
            sequenceAdminServiceClient.isExistingSequence("xyz");
        } catch (AxisFault axisFault) {
            // test for axisFault
        }

        try {
            sequenceAdminServiceClient.isExistingSequenceForTenant("xyz", "abc.com");
        } catch (AxisFault axisFault) {
            // test for axisFault
        }
    }

}
