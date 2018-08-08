/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.consent.mgt.core.dao.impl;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wso2.carbon.consent.mgt.core.dao.PurposeDAO;
import org.wso2.carbon.consent.mgt.core.exception.ConsentManagementClientException;
import org.wso2.carbon.consent.mgt.core.exception.ConsentManagementServerException;
import org.wso2.carbon.consent.mgt.core.internal.ConsentManagerComponentDataHolder;
import org.wso2.carbon.consent.mgt.core.model.Purpose;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.wso2.carbon.consent.mgt.core.util.TestUtils.closeH2Base;
import static org.wso2.carbon.consent.mgt.core.util.TestUtils.getConnection;
import static org.wso2.carbon.consent.mgt.core.util.TestUtils.initiateH2Base;
import static org.wso2.carbon.consent.mgt.core.util.TestUtils.mockComponentDataHolder;
import static org.wso2.carbon.consent.mgt.core.util.TestUtils.spyConnection;
import static org.wso2.carbon.consent.mgt.core.util.TestUtils.spyConnectionWithError;

@PrepareForTest(ConsentManagerComponentDataHolder.class)
public class PurposeDAOImplTest extends PowerMockTestCase {

    private static List<Purpose> purposes = new ArrayList<>();

    @BeforeMethod
    public void setUp() throws Exception {

        initiateH2Base();
        Purpose purpose1 = new Purpose("P1", "D1", "SIGNUP", "RESIDENT", -1234);
        Purpose purpose2 = new Purpose("P2", "D2", "JIT", "IDP", -1234);
        purposes.add(purpose1);
        purposes.add(purpose2);
    }

    @AfterMethod
    public void tearDown() throws Exception {

        closeH2Base();
    }

    @DataProvider(name = "purposeListProvider")
    public Object[][] provideListData() throws Exception {

        return new Object[][]{
                // limit, offset, tenantId, resultSize
                {0, 0, -1234, 0},
                {1, 1, -1234, 1},
                {10, 0, -1234, 3}
        };
    }

    @Test
    public void testAddPurpose() throws Exception {

        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try (Connection connection = getConnection()) {

            when(dataSource.getConnection()).thenReturn(connection);

            PurposeDAO purposeDAO = new PurposeDAOImpl();
            Purpose purposeResult = purposeDAO.addPurpose(purposes.get(0));

            Assert.assertEquals(purposeResult.getName(), purposes.get(0).getName());
            Assert.assertEquals(purposeResult.getDescription(), purposes.get(0).getDescription());
            Assert.assertEquals(purposeResult.getGroup(), purposes.get(0).getGroup());
            Assert.assertEquals(purposeResult.getGroupType(), purposes.get(0).getGroupType());
            Assert.assertEquals(purposeResult.getTenantId(), purposes.get(0).getTenantId());
        }
    }

    @Test(expectedExceptions = ConsentManagementServerException.class)
    public void testAddDuplicatePurpose() throws Exception {

        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try (Connection connection = getConnection()) {

            Connection spy = spyConnection(connection);
            when(dataSource.getConnection()).thenReturn(spy);

            PurposeDAO purposeDAO = new PurposeDAOImpl();
            purposeDAO.addPurpose(purposes.get(0));
            purposeDAO.addPurpose(purposes.get(0));

            Assert.fail("Expected: " + ConsentManagementServerException.class.getName());
        }
    }

    @Test
    public void testGetPurposeById() throws Exception {

        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try (Connection connection = getConnection()) {

            Connection spyConnection = spyConnection(connection);
            when(dataSource.getConnection()).thenReturn(spyConnection);

            PurposeDAO purposeDAO = new PurposeDAOImpl();
            Purpose purposeResult = purposeDAO.addPurpose(purposes.get(0));
            Assert.assertEquals(purposeResult.getName(), purposes.get(0).getName());

            Purpose purposeById = purposeDAO.getPurposeByUniqueId(purposeResult.getUniqueId());

            Assert.assertEquals(purposeById.getUniqueId(), purposeResult.getUniqueId());
            Assert.assertEquals(purposeById.getName(), purposeResult.getName());
            Assert.assertEquals(purposeById.getDescription(), purposeResult.getDescription());
        }
    }

    @Test(expectedExceptions = ConsentManagementClientException.class)
    public void testGetPurposeByInvalidId() throws Exception {

        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try (Connection connection = getConnection()) {

            Connection spyConnection = spyConnection(connection);
            when(dataSource.getConnection()).thenReturn(spyConnection);

            PurposeDAO purposeDAO = new PurposeDAOImpl();
            purposeDAO.getPurposeByUniqueId(0);

            Assert.fail("Expected: " + ConsentManagementClientException.class.getName());
        }
    }

    @Test(expectedExceptions = ConsentManagementServerException.class)
    public void testGetPurposeByIdWithException() throws Exception {

        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try (Connection connection = getConnection()) {

            Connection spyConnection = spyConnectionWithError(connection);
            when(dataSource.getConnection()).thenReturn(spyConnection);

            PurposeDAO purposeDAO = new PurposeDAOImpl();
            purposeDAO.getPurposeByUniqueId(1);

            Assert.fail("Expected: " + ConsentManagementServerException.class.getName());
        }
    }


    @Test(dataProvider = "purposeListProvider")
    public void testListPurposes(int limit, int offset, int tenantId, int resultSize) throws Exception {

        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try (Connection connection = getConnection()) {

            Connection spyConnection = spyConnection(connection);
            when(dataSource.getConnection()).thenReturn(spyConnection);

            PurposeDAO purposeDAO = new PurposeDAOImpl();

            Purpose purposeResult1 = purposeDAO.addPurpose(purposes.get(0));
            Assert.assertEquals(purposeResult1.getName(), purposes.get(0).getName());

            Purpose purposeResult2 = purposeDAO.addPurpose(purposes.get(1));
            Assert.assertEquals(purposeResult2.getName(), purposes.get(1).getName());

            List<Purpose> purposeList = purposeDAO.listPurposes(limit, offset, tenantId);

            Assert.assertEquals(purposeList.size(), resultSize);

            if (resultSize == 1) {
                Assert.assertEquals(purposeList.get(0).getName(), purposes.get(offset + 1).getName());
            }
        }
    }

    @Test(expectedExceptions = ConsentManagementServerException.class)
    public void testListPurposesWithException() throws Exception {

        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try (Connection connection = getConnection()) {

            Connection spyConnection = spyConnectionWithError(connection);
            when(dataSource.getConnection()).thenReturn(spyConnection);

            PurposeDAO purposeDAO = new PurposeDAOImpl();
            purposeDAO.listPurposes(0, 0, 0);

            Assert.fail("Expected: " + ConsentManagementServerException.class.getName());
        }
    }

    @Test
    public void testDeletePurpose() throws Exception {

        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try (Connection connection = getConnection()) {

            Connection spyConnection = spyConnection(connection);
            when(dataSource.getConnection()).thenReturn(spyConnection);

            PurposeDAO purposeDAO = new PurposeDAOImpl();
            Purpose purposeResult = purposeDAO.addPurpose(purposes.get(0));
            Assert.assertEquals(purposeResult.getName(), purposes.get(0).getName());

            String id = purposeDAO.deletePurpose(purposeResult.getPurposeId());

            Assert.assertEquals(id, purposeResult.getPurposeId());
        }
    }

    @Test(expectedExceptions = ConsentManagementServerException.class)
    public void testDeletePurposeWithException() throws Exception {

        DataSource dataSource = mock(DataSource.class);
        mockComponentDataHolder(dataSource);

        try (Connection connection = getConnection()) {

            Connection spyConnection = spyConnectionWithError(connection);
            when(dataSource.getConnection()).thenReturn(spyConnection);

            PurposeDAO purposeDAO = new PurposeDAOImpl();
            purposeDAO.deletePurpose("0000");

            Assert.fail("Expected: " + ConsentManagementServerException.class.getName());
        }
    }
}
