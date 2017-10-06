/*
 *   Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
 *
 */
package org.wso2.carbon.apimgt.impl.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.wso2.carbon.apimgt.api.model.Tier;
import org.wso2.carbon.apimgt.api.model.policy.PolicyConstants;
import org.wso2.carbon.apimgt.api.model.policy.QuotaPolicy;
import org.wso2.carbon.apimgt.api.model.policy.RequestCountLimit;
import org.wso2.carbon.apimgt.api.model.policy.SubscriptionPolicy;
import org.wso2.carbon.apimgt.impl.dao.ApiMgtDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ApiMgtDAO.class,LogFactory.class})
public class APIUtilTierTest {
    private final String[] validTierNames = {"Gold", "Silver", "Bronze", "Platinum", "Medium", "100PerMinute", "50PerMinute"};
    private static Log log;
    private static ApiMgtDAO apiMgtDAO;

    @BeforeClass
    public static void setup() {
        PowerMockito.mockStatic(LogFactory.class);
        log = Mockito.mock(Log.class);
        Mockito.when(LogFactory.getLog(Mockito.any(Class.class))).thenReturn(log);

        apiMgtDAO = Mockito.mock(ApiMgtDAO.class);
        PowerMockito.mockStatic(ApiMgtDAO.class);
        Mockito.when(ApiMgtDAO.getInstance()).thenReturn(apiMgtDAO);
    }

    /**
     * Test whether the APIUtil properly converts the billing plan and the custom attributes in the SubscriptionPolicy
     * when constructing the Tier object.
     */
    @Test
    public void testBillingPlanAndCustomAttr() throws Exception {
        SubscriptionPolicy policy = new SubscriptionPolicy("JUnitTest");
        JSONArray jsonArray = new JSONArray();

        JSONObject json1 = new JSONObject();
        json1.put("name", "key1");
        json1.put("value", "value1");
        jsonArray.add(json1);

        JSONObject json2 = new JSONObject();
        json2.put("name", "key2");
        json2.put("value", "value2");
        jsonArray.add(json2);

        policy.setCustomAttributes(jsonArray.toJSONString().getBytes());
        policy.setBillingPlan("FREE");

        Tier tier = new Tier("JUnitTest");

        APIUtil.setBillingPlanAndCustomAttributesToTier(policy, tier);

        Assert.assertTrue("Expected FREE but received " + tier.getTierPlan(), "FREE".equals(tier.getTierPlan()));

        if("key1".equals(tier.getTierAttributes().get("name"))){
            Assert.assertTrue("Expected to have 'value1' as the value of 'key1' but found " +
                            tier.getTierAttributes().get("value"),
                    tier.getTierAttributes().get("value").equals("value1"));
        }
        if("key2".equals(tier.getTierAttributes().get("name"))){
            Assert.assertTrue("Expected to have 'value2' as the value of 'key2' but found " +
                            tier.getTierAttributes().get("value"),
                    tier.getTierAttributes().get("value").equals("value2"));
        }
    }

    @Test
    public void testGetAvailableTiersWithExistingTier() throws Exception {
        Map<String, Tier> definedTiers = getDefinedTiers();

        // Select valid tier names to be assigned to the API
        Set<Tier> expectedTiers = getRoundRobinTierString(validTierNames);

        String apiName = "testApi";

        Set<Tier> availableTiers = APIUtil.getAvailableTiers(definedTiers, getTiersAsString(expectedTiers), apiName);

        Assert.assertEquals("Expected tiers do not match", expectedTiers, availableTiers);
    }

    @Test
    public void testGetAvailableTiersWithNonExisting() throws Exception {
        Map<String, Tier> definedTiers = getDefinedTiers();

        // Select valid tier names to be assigned to the API
        Set<Tier> expectedTiers = getRoundRobinTierString(validTierNames);

        Set<Tier> assignedTiers = new HashSet<Tier>(expectedTiers);
        assignedTiers.add(new Tier("Bogus"));

        String apiName = "testApi";

        Set<Tier> availableTiers = APIUtil.getAvailableTiers(definedTiers, getTiersAsString(assignedTiers), apiName);
        Assert.assertEquals("Expected tiers do not match", expectedTiers, availableTiers);

    }

    @Test
    public void testGetTiersFromPolicies() throws Exception {
        String policyLevel = PolicyConstants.POLICY_LEVEL_SUB;
        int tenantId = 1;

        SubscriptionPolicy[] policies = generateSubscriptionPolicies(new String[]{"policy1", "gold"});
        Mockito.when(apiMgtDAO.getSubscriptionPolicies(tenantId)).thenReturn(policies);


        Map<String, Tier> tiersFromPolicies = APIUtil.getTiersFromPolicies(policyLevel, tenantId);

        for (SubscriptionPolicy policy : policies) {
            Tier tier = tiersFromPolicies.get(policy.getPolicyName());
            Assert.assertNotNull(tier);
            Assert.assertEquals(policy.getPolicyName(), tier.getName());
            Assert.assertEquals(policy.getBillingPlan(), tier.getTierPlan());
            Assert.assertEquals(policy.getDescription(), tier.getDescription());

        }
        
    }

    private SubscriptionPolicy[] generateSubscriptionPolicies(String[] policyNames) {
        List<SubscriptionPolicy> policyList = new ArrayList<>();

        for (String policyName : policyNames) {
            SubscriptionPolicy policy = new SubscriptionPolicy(policyName);
            policy.setBillingPlan("default");
            policy.setStopOnQuotaReach(true);
            policy.setRateLimitCount(1000);
            policy.setRateLimitTimeUnit("PerMinute");

            QuotaPolicy quotaPolicy = new QuotaPolicy();
            RequestCountLimit countLimit = new RequestCountLimit();
            countLimit.setRequestCount(123);
            quotaPolicy.setLimit(countLimit);

            policy.setDefaultQuotaPolicy(quotaPolicy);
            policy.setDescription(policyName);
            policyList.add(policy);
        }

        SubscriptionPolicy[] array = {};
        return policyList.toArray(array);
    }

    private Set<Tier> getRoundRobinTierString(String[] values) {
        Set<Tier> tiers = new HashSet<Tier>();
        for (int i = 0; i < values.length; ++i) {
            if (i % 2 == 0) {
                tiers.add(new Tier(values[i]));
            }
        }

        return tiers;
    }

    private Map<String, Tier> getDefinedTiers() {
        Map<String, Tier> definedTiers = new HashMap<String, Tier>();

        for (String tierName : validTierNames) {
            definedTiers.put(tierName, new Tier(tierName));
        }

        return definedTiers;
    }

    private String getTiersAsString(Set<Tier> tiers) {
        StringBuilder builder = new StringBuilder();

        for (Tier tier : tiers) {
            builder.append(tier.getName());
            builder.append("||");
        }

        return builder.toString();
    }
}
