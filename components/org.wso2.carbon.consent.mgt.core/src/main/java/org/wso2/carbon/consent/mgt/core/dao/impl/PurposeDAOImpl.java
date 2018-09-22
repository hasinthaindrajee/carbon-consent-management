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

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.consent.mgt.core.dao.PurposeDAO;
import org.wso2.carbon.consent.mgt.core.exception.ConsentManagementException;
import org.wso2.carbon.consent.mgt.core.exception.ConsentManagementServerException;
import org.wso2.carbon.consent.mgt.core.model.Purpose;
import org.wso2.carbon.consent.mgt.core.model.PurposeEntityAssociation;
import org.wso2.carbon.consent.mgt.core.model.PurposePIICategory;
import org.wso2.carbon.consent.mgt.core.util.ConsentUtils;
import org.wso2.carbon.consent.mgt.core.util.JdbcUtils;
import org.wso2.carbon.database.utils.jdbc.JdbcTemplate;
import org.wso2.carbon.database.utils.jdbc.exceptions.DataAccessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.wso2.carbon.consent.mgt.core.constant.ConsentConstants.ErrorMessages;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.DELETE_PURPOSE_ENTITY_ASSOC_BY_PURPOSE_ID_SQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.DELETE_PURPOSE_ENTITY_ASSOC_SQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.DELETE_PURPOSE_SQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.GET_PURPOSE_BY_ID_SQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.GET_PURPOSE_BY_NAME_SQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.GET_PURPOSE_ENTITY_ASSOC_SQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.GET_PURPOSE_PII_CAT_SQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.GET_RECEIPT_COUNT_ASSOCIATED_WITH_PURPOSE;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.INSERT_PURPOSE_ENTITY_ASSOC_SQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.INSERT_PURPOSE_SQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.INSERT_RECEIPT_PURPOSE_PII_ASSOC_SQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.LIST_PAGINATED_PURPOSE_DB2;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.LIST_PAGINATED_PURPOSE_INFORMIX;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.LIST_PAGINATED_PURPOSE_MSSQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.LIST_PAGINATED_PURPOSE_MYSQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.LIST_PAGINATED_PURPOSE_ORACLE;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_ENTITY_NAME_TYPE;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_ENTITY_NAME_TYPE_DB2;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_ENTITY_NAME_TYPE_INFORMIX;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_ENTITY_NAME_TYPE_MSSQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_ENTITY_NAME_TYPE_ORACLE;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_PURPOSE_ID;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_PURPOSE_ID_DB2;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_PURPOSE_ID_INFORMIX;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_PURPOSE_ID_MSSQL;
import static org.wso2.carbon.consent.mgt.core.constant.SQLConstants.SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_PURPOSE_ID_ORACLE;
import static org.wso2.carbon.consent.mgt.core.util.JdbcUtils.isDB2DB;
import static org.wso2.carbon.consent.mgt.core.util.JdbcUtils.isH2MySqlOrPostgresDB;
import static org.wso2.carbon.consent.mgt.core.util.JdbcUtils.isInformixDB;
import static org.wso2.carbon.consent.mgt.core.util.JdbcUtils.isMSSqlDB;
import static org.wso2.carbon.consent.mgt.core.util.LambdaExceptionUtils.rethrowConsumer;

/**
 * Default implementation of {@link PurposeDAO}. This handles {@link Purpose} related DB operations.
 */
public class PurposeDAOImpl implements PurposeDAO {

    private static final String SQL_FILTER_STRING_ANY = "%";
    private static final String QUERY_FILTER_STRING_ANY = "*";
    private static final String QUERY_FILTER_STRING_ANY_ESCAPED = "\\*";

    public PurposeDAOImpl() {

    }

    @Override
    public int getPriority() {

        return 1;
    }

    @Override
    public Purpose addPurpose(Purpose purpose) throws ConsentManagementException {

        Purpose purposeResult;
        int insertedId;

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            insertedId = jdbcTemplate.executeInsert(INSERT_PURPOSE_SQL, (preparedStatement -> {
                preparedStatement.setString(1, purpose.getName());
                preparedStatement.setString(2, purpose.getDescription());
                preparedStatement.setString(3, purpose.getGroup());
                preparedStatement.setString(4, purpose.getGroupType());
                preparedStatement.setInt(5, purpose.getTenantId());
            }), purpose, true);
        } catch (DataAccessException e) {
            throw ConsentUtils.handleServerException(ErrorMessages.ERROR_CODE_ADD_PURPOSE, purpose.getName(), e);
        }

        purpose.getPurposePIICategories().forEach(rethrowConsumer(piiCategory -> {
            try {
                jdbcTemplate.executeInsert(INSERT_RECEIPT_PURPOSE_PII_ASSOC_SQL, (preparedStatement -> {
                    preparedStatement.setInt(1, insertedId);
                    preparedStatement.setInt(2, piiCategory.getId());
                    preparedStatement.setInt(3, piiCategory.getMandatory() ? 1 : 0);
                }), piiCategory, false);
            } catch (DataAccessException e) {
                throw ConsentUtils.handleServerException(ErrorMessages
                        .ERROR_CODE_ADD_PURPOSE_PII_ASSOC, String.valueOf(insertedId), e);
            }
        }));
        purposeResult = new Purpose(insertedId, purpose.getName(), purpose.getDescription(), purpose.getGroup(),
                purpose.getGroupType(), purpose.getTenantId(), purpose
                .getPurposePIICategories());
        return purposeResult;
    }

    @Override
    public Purpose getPurposeById(int id) throws ConsentManagementException {

        if (id == 0) {
            throw ConsentUtils.handleClientException(ErrorMessages.ERROR_CODE_PURPOSE_ID_REQUIRED, null);
        }

        Purpose purpose;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            purpose = jdbcTemplate.fetchSingleRecord(GET_PURPOSE_BY_ID_SQL, (resultSet, rowNumber) ->
                            new Purpose(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                                    resultSet.getString(4), resultSet.getString(5),
                                    resultSet.getInt(6)),
                    preparedStatement -> preparedStatement.setInt(1, id));
        } catch (DataAccessException e) {
            throw ConsentUtils.handleServerException(ErrorMessages.ERROR_CODE_SELECT_PURPOSE_BY_ID, String.valueOf(id), e);
        }

        if (purpose != null) {
            try {
                List<PurposePIICategory> piiCategories = new ArrayList<>();
                jdbcTemplate.executeQuery(GET_PURPOSE_PII_CAT_SQL, (resultSet, rowNumber) ->
                                piiCategories.add(new PurposePIICategory(
                                        resultSet.getInt(1),
                                        resultSet.getInt(2) == 1)),
                        preparedStatement -> preparedStatement.setInt(1, purpose.getId()));
                purpose.setPurposePIICategories(piiCategories);
            } catch (DataAccessException e) {
                throw ConsentUtils.handleServerException(ErrorMessages.ERROR_CODE_SELECT_PURPOSE_BY_ID, String.valueOf(id), e);
            }
        }
        return purpose;
    }

    @Override
    public Purpose getPurposeByName(String name, String group, String groupType, int tenantId) throws
            ConsentManagementException {

        if (StringUtils.isBlank(name)) {
            throw ConsentUtils.handleClientException(ErrorMessages.ERROR_CODE_PURPOSE_NAME_REQUIRED, null);
        }

        if (StringUtils.isBlank(group)) {
            throw ConsentUtils.handleClientException(ErrorMessages.ERROR_CODE_PURPOSE_GROUP_REQUIRED, null);
        }

        if (StringUtils.isBlank(groupType)) {
            throw ConsentUtils.handleClientException(ErrorMessages.ERROR_CODE_PURPOSE_GROUP_TYPE_REQUIRED, null);
        }

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        Purpose purpose;

        try {
            purpose = jdbcTemplate.fetchSingleRecord(GET_PURPOSE_BY_NAME_SQL,
                    (resultSet, rowNumber) -> new Purpose(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6)),
                    preparedStatement -> {
                        preparedStatement.setString(1, name);
                        preparedStatement.setString(2, group);
                        preparedStatement.setString(3, groupType);
                        preparedStatement.setInt(4, tenantId);
                    });
        } catch (DataAccessException e) {
            throw ConsentUtils.handleServerException(ErrorMessages.ERROR_CODE_SELECT_PURPOSE_BY_NAME, name, e);
        }
        return purpose;
    }

    @Override
    public List<Purpose> listPurposes(int limit, int offset, int tenantId) throws ConsentManagementException {

        return listPurposes(QUERY_FILTER_STRING_ANY, QUERY_FILTER_STRING_ANY, limit, offset, tenantId);
    }

    @Override
    public List<Purpose> listPurposes(String group, String groupType, int limit, int offset, int tenantId) throws
            ConsentManagementException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        List<Purpose> purposes;
        try {

            if (StringUtils.isEmpty(group)) {
                group = SQL_FILTER_STRING_ANY;
            } else if (group.contains(QUERY_FILTER_STRING_ANY)) {
                group = group.replaceAll(QUERY_FILTER_STRING_ANY_ESCAPED, SQL_FILTER_STRING_ANY);
            }

            if (StringUtils.isEmpty(groupType)) {
                groupType = SQL_FILTER_STRING_ANY;
            } else if (groupType.contains(QUERY_FILTER_STRING_ANY)) {
                groupType = groupType.replaceAll(QUERY_FILTER_STRING_ANY_ESCAPED, SQL_FILTER_STRING_ANY);
            }

            String query;
            if (isH2MySqlOrPostgresDB()) {
                query = LIST_PAGINATED_PURPOSE_MYSQL;
            } else if (isDB2DB()) {
                query = LIST_PAGINATED_PURPOSE_DB2;
                int initialOffset = offset;
                offset = offset + limit;
                limit = initialOffset + 1;
            } else if (isMSSqlDB()) {
                int initialOffset = offset;
                offset = limit + offset;
                limit = initialOffset + 1;
                query = LIST_PAGINATED_PURPOSE_MSSQL;
            } else if (isInformixDB()) {
                query = LIST_PAGINATED_PURPOSE_INFORMIX;
            } else {
                //oracle
                query = LIST_PAGINATED_PURPOSE_ORACLE;
                limit = offset + limit;
            }
            int finalLimit = limit;
            int finalOffset = offset;
            String finalGroup = group;
            String finalGroupType = groupType;

            purposes = jdbcTemplate.executeQuery(query,
                    (resultSet, rowNumber) -> new Purpose(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6)),
                    preparedStatement -> {
                        preparedStatement.setInt(1, tenantId);
                        preparedStatement.setString(2, finalGroup);
                        preparedStatement.setString(3, finalGroupType);
                        preparedStatement.setInt(4, finalLimit);
                        preparedStatement.setInt(5, finalOffset);
                    });
        } catch (DataAccessException e) {
            throw new ConsentManagementServerException(String.format(ErrorMessages.ERROR_CODE_LIST_PURPOSE.getMessage(),
                    group, groupType, limit, offset), ErrorMessages.ERROR_CODE_LIST_PURPOSE.getCode(), e);
        }
        return purposes;
    }

    @Override
    public int deletePurpose(int id) throws ConsentManagementException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(DELETE_PURPOSE_SQL, preparedStatement -> preparedStatement.setInt(1, id));
            deletePurposeEntityAssociations(String.valueOf(id));
        } catch (DataAccessException e) {
            throw ConsentUtils.handleServerException(ErrorMessages.ERROR_CODE_DELETE_PURPOSE, String.valueOf(id), e);
        }

        return id;
    }

    /**
     * Check whether the {@link Purpose} by ID is used in a receipt
     *
     * @param id ID of the {@link Purpose} to be validated
     * @return true if purpose is used, false otherwise.
     */
    @Override
    public boolean isPurposeUsed(int id) throws ConsentManagementServerException {

        Integer count;
        try {
            JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
            count = jdbcTemplate.fetchSingleRecord(GET_RECEIPT_COUNT_ASSOCIATED_WITH_PURPOSE, (resultSet, rowNumber) ->
                            resultSet.getInt(1),
                    preparedStatement -> preparedStatement.setInt(1, id));
            if (count == null) {
                return false;
            }
        } catch (DataAccessException e) {
            throw ConsentUtils.handleServerException(ErrorMessages
                    .ERROR_CODE_RETRIEVE_RECEIPTS_ASSOCIATED_WITH_PURPOSE, String.valueOf(id), e);
        }
        return (count > 0);
    }

    public PurposeEntityAssociation addPurposeEntityAssociation(PurposeEntityAssociation purposeEntityAssociation)
            throws ConsentManagementServerException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        PurposeEntityAssociation resultPurposeEntityAssociation;
        int InsertedId;
        try {
            InsertedId = jdbcTemplate.executeInsert(INSERT_PURPOSE_ENTITY_ASSOC_SQL, (preparedStatement -> {
                preparedStatement.setString(1, purposeEntityAssociation.getPurposeId());
                preparedStatement.setString(2, purposeEntityAssociation.getExternalEntityName());
                preparedStatement.setString(3, purposeEntityAssociation.getExternalEntityType());
                preparedStatement.setInt(4, purposeEntityAssociation.getTenantId());
            }), purposeEntityAssociation, true);
        } catch (DataAccessException e) {
            throw ConsentUtils.handleServerException(ErrorMessages
                    .ERROR_CODE_ADD_PURPOSE_EXTERNAL_ENTITY_ASSOCICATION, purposeEntityAssociation.getPurposeId(), e);
        }
        resultPurposeEntityAssociation = new PurposeEntityAssociation(InsertedId, purposeEntityAssociation
                .getPurposeId(), purposeEntityAssociation.getExternalEntityName(), purposeEntityAssociation
                .getExternalEntityType(), purposeEntityAssociation.getTenantId());
        return resultPurposeEntityAssociation;
    }

    public PurposeEntityAssociation getPurposeEntityAssociation(int associationId, int tenantId) throws
            ConsentManagementException {

        if (associationId == 0) {
            throw ConsentUtils.handleClientException(ErrorMessages.ERROR_CODE_PURPOSE_ASSOCIATION_ID_REQUIRED, null);
        }

        PurposeEntityAssociation purposeEntityAssociation;
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            purposeEntityAssociation = jdbcTemplate.fetchSingleRecord(GET_PURPOSE_ENTITY_ASSOC_SQL, (resultSet, rowNumber) ->

                            new PurposeEntityAssociation(resultSet.getInt(1),
                                    resultSet.getString(2), resultSet.getString(3),
                                    resultSet.getString(4), tenantId),

                    preparedStatement -> {
                        preparedStatement.setInt(1, associationId);
                        preparedStatement.setInt(2, tenantId);
                    });
            return purposeEntityAssociation;
        } catch (DataAccessException e) {
            throw ConsentUtils.handleServerException(ErrorMessages.ERROR_CODE_SELECT_PURPOSE_BY_ID, String.valueOf(associationId)
                    , e);
        }
    }

    public void deletePurposeEntityAssociation(int associationId, int tenantId)
            throws ConsentManagementException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(DELETE_PURPOSE_ENTITY_ASSOC_SQL, (preparedStatement -> {
                preparedStatement.setInt(1, associationId);
                preparedStatement.setInt(2, tenantId);
            }));
        } catch (DataAccessException e) {
            throw ConsentUtils.handleServerException(ErrorMessages
                    .ERROR_CODE_DELETE_PURPOSE_EXTERNAL_ENTITY_ASSOCICATION, Integer.toString(associationId), e);
        }

    }

    public List<PurposeEntityAssociation> listPurposeAssociations(String purposeId, String externalEntityName, String
            externalEntityType, int tenantId, int limit, int offset) throws ConsentManagementException {

        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        boolean searchFromPurposeId = true;
        if (StringUtils.isEmpty(purposeId)) {
            searchFromPurposeId = false;
        }
        try {

            if (StringUtils.isEmpty(externalEntityName)) {
                externalEntityName = SQL_FILTER_STRING_ANY;
            } else if (externalEntityName.contains(QUERY_FILTER_STRING_ANY)) {
                externalEntityName = externalEntityType.replaceAll(QUERY_FILTER_STRING_ANY_ESCAPED,
                        SQL_FILTER_STRING_ANY);
            }

            if (StringUtils.isEmpty(externalEntityType)) {
                externalEntityType = SQL_FILTER_STRING_ANY;
            } else if (externalEntityType.contains(QUERY_FILTER_STRING_ANY)) {
                externalEntityType = externalEntityType.replaceAll(QUERY_FILTER_STRING_ANY_ESCAPED,
                        SQL_FILTER_STRING_ANY);
            }

            List<PurposeEntityAssociation> results;
            int finalLimit = getLimit(offset, limit);
            int finalOffset = getOffset(offset, limit);
            String query;
            if (searchFromPurposeId) {
                query = getListPurposeEntityAssocSQL(true);
                results = getPurposeEntityAssociationsById(purposeId, tenantId, finalLimit, finalOffset, jdbcTemplate,
                        externalEntityName, externalEntityType, query);
            } else {
                query = getListPurposeEntityAssocSQL(false);
                results = getPurposeEntityAssociationsByNameType(tenantId, finalLimit, finalOffset, jdbcTemplate,
                        externalEntityName, externalEntityType, query);
            }

            return results;

        } catch (DataAccessException e) {
            throw new ConsentManagementServerException(String.
                    format(ErrorMessages.ERROR_CODE_LIST_PURPOSE_EXT_ENTITY_ASSOC.getMessage(),
                            externalEntityName, externalEntityType, limit, offset),
                    ErrorMessages.ERROR_CODE_LIST_PURPOSE_EXT_ENTITY_ASSOC.getCode(), e);
        }
    }

    public void deletePurposeEntityAssociations(String purposeId) throws ConsentManagementException {
        JdbcTemplate jdbcTemplate = JdbcUtils.getNewTemplate();
        try {
            jdbcTemplate.executeUpdate(DELETE_PURPOSE_ENTITY_ASSOC_BY_PURPOSE_ID_SQL, (preparedStatement ->
                    preparedStatement.setString(1, purposeId)));
        } catch (DataAccessException e) {
            throw ConsentUtils.handleServerException(ErrorMessages
                    .ERROR_CODE_DELETE_PURPOSE_EXTERNAL_ENTITY_ASSOCICATION, purposeId, e);
        }

    }
    private int getOffset(int offset, int limit) throws DataAccessException {
        int  finalOffset = offset;
       if (isDB2DB()) {
           finalOffset = offset + limit;
        } else if (isMSSqlDB()) {
           finalOffset = limit + offset;
        }
        return finalOffset;
    }

    private int getLimit(int offset, int limit) throws DataAccessException {
        int finalLimit;
        if (isH2MySqlOrPostgresDB()) {
            finalLimit = limit;
        } else if (isDB2DB()) {
            int initialOffset = offset;
            finalLimit = initialOffset + 1;
        } else if (isMSSqlDB()) {
            int initialOffset = offset;
            finalLimit = initialOffset + 1;
        } else if (isInformixDB()) {
            finalLimit = limit;
        } else {
            finalLimit = offset + limit;
        }
        return finalLimit;
    }

    private String getListPurposeEntityAssocSQL(boolean searchFromId) throws DataAccessException {

        String query;
        if (searchFromId) {
            if (isH2MySqlOrPostgresDB()) {
                query = SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_PURPOSE_ID;
            } else if (isDB2DB()) {
                query = SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_PURPOSE_ID_DB2;
            } else if (isMSSqlDB()) {
                query = SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_PURPOSE_ID_MSSQL;
            } else if (isInformixDB()) {
                query = SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_PURPOSE_ID_INFORMIX;
            } else {
                query = SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_PURPOSE_ID_ORACLE;
            }
        } else {
            if (isH2MySqlOrPostgresDB()) {
                query = SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_ENTITY_NAME_TYPE;
            } else if (isDB2DB()) {
                query = SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_ENTITY_NAME_TYPE_DB2;
            } else if (isMSSqlDB()) {
                query = SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_ENTITY_NAME_TYPE_MSSQL;
            } else if (isInformixDB()) {
                query = SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_ENTITY_NAME_TYPE_INFORMIX;
            } else {
                //oracle
                query = SEARCH_PURPOSE_ENTITY_ASSOCIATIONS_BY_ENTITY_NAME_TYPE_ORACLE;
            }
        }
        return query;
    }
    private List<PurposeEntityAssociation> getPurposeEntityAssociationsByNameType(int tenantId, int limit, int offset,
                                                                                  JdbcTemplate jdbcTemplate,
                                                                                  String finalExternalEntityName,
                                                                                  String finalExternalEntityType,
                                                                                  String query) throws
            DataAccessException {

        List<PurposeEntityAssociation> results;
        results = jdbcTemplate.executeQuery(query,
                (resultSet, rowNumber) -> new PurposeEntityAssociation(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        tenantId),
                preparedStatement -> {
                    preparedStatement.setString(1, finalExternalEntityName);
                    preparedStatement.setString(2, finalExternalEntityType);
                    preparedStatement.setInt(3, tenantId);
                    preparedStatement.setInt(5, limit);
                    preparedStatement.setInt(6, offset);
                });
        return results;
    }

    private List<PurposeEntityAssociation> getPurposeEntityAssociationsById(String purposeId, int tenantId, int limit,
                                                                            int offset, JdbcTemplate jdbcTemplate,
                                                                            String finalExternalEntityName,
                                                                            String finalExternalEntityType,
                                                                            String query) throws DataAccessException {

        List<PurposeEntityAssociation> results;
        results = jdbcTemplate.executeQuery(query,
                (resultSet, rowNumber) -> new PurposeEntityAssociation(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        tenantId),
                preparedStatement -> {
                    preparedStatement.setString(1, purposeId);
                    preparedStatement.setString(2, finalExternalEntityName);
                    preparedStatement.setString(3, finalExternalEntityType);
                    preparedStatement.setInt(4, tenantId);
                    preparedStatement.setInt(5, limit);
                    preparedStatement.setInt(6, offset);
                });
        return results;
    }

}
