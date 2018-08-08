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

package org.wso2.carbon.consent.mgt.core.constant;

/**
 * Constant related to SQL operations.
 */
public class SQLConstants {

    public static final String INSERT_PURPOSE_SQL = "INSERT INTO CM_PURPOSE (PURPOSE_ID, VERSION, NAME,DESCRIPTION, " +
            "PURPOSE_GROUP, GROUP_TYPE, TENANT_ID) values (?, ?, ?, ?, ?, ?, ?)";

    public static final String GET_PURPOSE_BY_ID_SQL = "SELECT ID, PURPOSE_ID, VERSION, NAME, DESCRIPTION, " +
            "PURPOSE_GROUP, GROUP_TYPE, TENANT_ID FROM CM_PURPOSE WHERE ID = ?";

    public static final String GET_PURPOSE_BY_PURPOSE_ID_VERSION_SQL = "SELECT ID, PURPOSE_ID, VERSION, NAME, " +
            "DESCRIPTION, " +
            "PURPOSE_GROUP, GROUP_TYPE, TENANT_ID FROM CM_PURPOSE WHERE PURPOSE_ID = ? AND VERSION = ?";

    public static final String GET_PURPOSE_BY_PURPOSE_ID = "SELECT ID, PURPOSE_ID, VERSION, NAME, " +
            "DESCRIPTION, " +
            "PURPOSE_GROUP, GROUP_TYPE, TENANT_ID FROM CM_PURPOSE WHERE PURPOSE_ID = ?";

    public static final String GET_PURPOSE_BY_NAME_SQL = "SELECT ID, PURPOSE_ID, VERSION, NAME, DESCRIPTION, " +
            "PURPOSE_GROUP, " +
            "GROUP_TYPE, TENANT_ID FROM CM_PURPOSE WHERE NAME = ? AND PURPOSE_GROUP = ? AND GROUP_TYPE = ? AND TENANT_ID = ?";
    public static final String LIST_PAGINATED_PURPOSE_MYSQL = "SELECT ID, PURPOSE_ID, VERSION, NAME, DESCRIPTION, " +
            "PURPOSE_GROUP, " +
                                                              "GROUP_TYPE, TENANT_ID FROM CM_PURPOSE " +
                                                              "WHERE TENANT_ID = ? AND PURPOSE_GROUP LIKE ? AND " +
                                                              "GROUP_TYPE LIKE ? ORDER BY ID ASC LIMIT ? OFFSET ?";

    public static final String LIST_PAGINATED_PURPOSE_DB2 = "SELECT PURPOSE_ID, NAME, DESCRIPTION, PURPOSE_GROUP, " +
            "GROUP_TYPE, TENANT_ID FROM (SELECT ROW_NUMBER() OVER " +
                                                            "(ORDER BY ID) AS rn, p.* FROM CM_PURPOSE AS p) WHERE " +
                                                            "TENANT_ID =? AND PURPOSE_GROUP LIKE ? AND GROUP_TYPE " +
                                                            "LIKE ? rn BETWEEN ? AND ?";

    public static final String LIST_PAGINATED_PURPOSE_ORACLE = "SELECT PURPOSE_ID, NAME, DESCRIPTION, PURPOSE_GROUP, " +
                                                               "GROUP_TYPE, IS_MANDATORY, TENANT_ID FROM (SELECT ID, " +
                                                               "NAME, DESCRIPTION, TENANT_ID, rownum AS rnum FROM " +
                                                               "(SELECT ID, NAME, DESCRIPTION, PURPOSE_GROUP, " +
                                                               "GROUP_TYPE, IS_MANDATORY, TENANT_ID FROM CM_PURPOSE " +
                                                               "ORDER BY ID) WHERE TENANT_ID =? AND PURPOSE_GROUP" +
                                                               " LIKE ? AND GROUP_TYPE LIKE ? AND rownum <= ?) WHERE " +
                                                               "rnum > ?";

    public static final String LIST_PAGINATED_PURPOSE_MSSQL = "SELECT PURPOSE_ID, NAME, DESCRIPTION, PURPOSE_GROUP, " +
                                                              "GROUP_TYPE, IS_MANDATORY, TENANT_ID FROM (SELECT ID, " +
                                                              "NAME, DESCRIPTION, TENANT_ID, ROW_NUMBER() OVER (ORDER" +
                                                              " BY ID) AS RowNum FROM CM_PURPOSE) AS P WHERE P" +
                                                              ".TENANT_ID = ? AND PURPOSE_GROUP LIKE ? AND GROUP_TYPE" +
                                                              " LIKE ? AND P.RowNum BETWEEN ? AND ?";

    public static final String LIST_PAGINATED_PURPOSE_INFORMIX = "SELECT PURPOSE_ID, NAME, DESCRIPTION, " +
            "PURPOSE_GROUP, " +
                                                                 "GROUP_TYPE, IS_MANDATORY, TENANT_ID FROM CM_PURPOSE" +
                                                                 " WHERE TENANT_ID = ? AND PURPOSE_GROUP LIKE ? AND " +
                                                                 "GROUP_TYPE LIKE ? ORDER BY ID ASC LIMIT ? OFFSET ?";

    public static final String DELETE_PURPOSE_SQL = "DELETE FROM CM_PURPOSE WHERE PURPOSE_ID = ?";
    public static final String INSERT_PII_CATEGORY_SQL = "INSERT INTO CM_PII_CATEGORY (NAME, DESCRIPTION," +
            "IS_SENSITIVE, TENANT_ID, DISPLAY_NAME) VALUES (?,?,?,?,?)";
    public static final String SELECT_PII_CATEGORY_BY_ID_SQL = "SELECT ID, NAME, DESCRIPTION, IS_SENSITIVE, " +
            "TENANT_ID, DISPLAY_NAME FROM CM_PII_CATEGORY WHERE ID = ?";
    public static final String LIST_PAGINATED_PII_CATEGORY_MYSQL = "SELECT ID, NAME, DESCRIPTION, IS_SENSITIVE, " +
            "TENANT_ID,DISPLAY_NAME FROM CM_PII_CATEGORY WHERE TENANT_ID = " +
            "? ORDER BY ID ASC LIMIT ? OFFSET ?";

    public static final String LIST_PAGINATED_PII_CATEGORY_INFORMIX = "SELECT ID, NAME, DESCRIPTION, IS_SENSITIVE, " +
            "TENANT_ID,DISPLAY_NAME FROM CM_PII_CATEGORY WHERE TENANT_ID = " +
            "? ORDER BY ID ASC LIMIT ? OFFSET ?";

    public static final String LIST_PAGINATED_PII_CATEGORY_DB2 = "SELECT ID, NAME, DESCRIPTION, IS_SENSITIVE," +
            "TENANT_ID,DISPLAY_NAME FROM (SELECT ROW_NUMBER() OVER (ORDER BY ID) AS rn, p.*  FROM CM_PII_CATEGORY AS" +
            " p) WHERE TENANT_ID =? AND rn BETWEEN ? AND ?";

    public static final String LIST_PAGINATED_PII_CATEGORY_MSSQL = "SELECT ID, NAME, DESCRIPTION, IS_SENSITIVE," +
            "TENANT_ID,DISPLAY_NAME FROM " +
            "(SELECT ID, NAME, DESCRIPTION, IS_SENSITIVE,TENANT_ID,DISPLAY_NAME, ROW_NUMBER() OVER (ORDER BY ID) AS " +
            "RowNum FROM " +
            "CM_PII_CATEGORY) AS P WHERE P.TENANT_ID = ? AND P.RowNum BETWEEN ? AND ?";

    public static final String LIST_PAGINATED_PII_CATEGORY_ORACLE = "SELECT ID, NAME, DESCRIPTION, IS_SENSITIVE," +
            "TENANT_ID,DISPLAY_NAME FROM (SELECT ID, NAME, DESCRIPTION, IS_SENSITIVE,TENANT_ID,DISPLAY_NAME, rownum " +
            "AS rnum FROM (SELECT ID, NAME, DESCRIPTION, IS_SENSITIVE, TENANT_ID,DISPLAY_NAME FROM CM_PII_CATEGORY " +
            "ORDER BY ID) WHERE TENANT_ID =? AND rownum <= ?) WHERE  rnum > ?";

    public static final String DELETE_PII_CATEGORY_SQL = "DELETE FROM CM_PII_CATEGORY WHERE ID = ?";
    public static final String SELECT_PII_CATEGORY_BY_NAME_SQL = "SELECT ID, NAME, DESCRIPTION, IS_SENSITIVE, " +
            "TENANT_ID,DISPLAY_NAME FROM CM_PII_CATEGORY WHERE NAME = ? AND " +
            "TENANT_ID = ? ";
    public static final String INSERT_PURPOSE_CATEGORY_SQL = "INSERT INTO CM_PURPOSE_CATEGORY (NAME, DESCRIPTION, " +
            "TENANT_ID) VALUES (?,?,?)";
    public static final String SELECT_PURPOSE_CATEGORY_BY_ID_SQL = "SELECT ID, NAME, DESCRIPTION, TENANT_ID FROM " +
            "CM_PURPOSE_CATEGORY  WHERE ID = ?";
    public static final String LIST_PAGINATED_PURPOSE_CATEGORY_MYSQL = "SELECT ID, NAME, DESCRIPTION, TENANT_ID FROM " +
            "CM_PURPOSE_CATEGORY WHERE TENANT_ID = ? ORDER BY ID ASC LIMIT ? OFFSET ?";

    public static final String LIST_PAGINATED_PURPOSE_CATEGORY_INFORMIX = "SELECT ID, NAME, DESCRIPTION, TENANT_ID " +
            "FROM CM_PURPOSE_CATEGORY WHERE TENANT_ID = ? ORDER BY ID ASC LIMIT ? OFFSET ?";

    public static final String LIST_PAGINATED_PURPOSE_CATEGORY_DB2 = "SELECT ID, NAME, DESCRIPTION, TENANT_ID FROM " +
            "(SELECT ROW_NUMBER() OVER (ORDER BY ID) AS rn, p.*  FROM CM_PURPOSE_CATEGORY AS" +
            " p) WHERE TENANT_ID =? AND rn BETWEEN ? AND ?";

    public static final String LIST_PAGINATED_PURPOSE_CATEGORY_ORACLE = "SELECT ID, NAME, DESCRIPTION, TENANT_ID FROM " +
            "(SELECT ID, NAME, DESCRIPTION, TENANT_ID, rownum AS rnum " +
            "FROM (SELECT ID, NAME, DESCRIPTION, TENANT_ID FROM CM_PURPOSE_CATEGORY ORDER BY ID) WHERE " +
            "TENANT_ID =? AND rownum <= ?) WHERE  rnum > ?";

    public static final String LIST_PAGINATED_PURPOSE_CATEGORY_MSSQL = "SELECT ID, NAME, DESCRIPTION, TENANT_ID FROM " +
            "(SELECT ID, NAME, DESCRIPTION, TENANT_ID, ROW_NUMBER() OVER (ORDER BY ID) AS RowNum FROM " +
            "CM_PURPOSE_CATEGORY) AS P WHERE P.TENANT_ID = ? AND P.RowNum BETWEEN ? AND ?";

    public static final String DELETE_PURPOSE_CATEGORY_SQL = "DELETE FROM CM_PURPOSE_CATEGORY WHERE ID = ?";
    public static final String SELECT_PURPOSE_CATEGORY_BY_NAME_SQL = "SELECT ID, NAME, DESCRIPTION, TENANT_ID FROM " +
            "CM_PURPOSE_CATEGORY WHERE NAME = ? AND TENANT_ID = ?";

    public static final String INSERT_RECEIPT_SQL = "INSERT INTO CM_RECEIPT (CONSENT_RECEIPT_ID,VERSION, " +
            "JURISDICTION,CONSENT_TIMESTAMP,COLLECTION_METHOD,LANGUAGE,PII_PRINCIPAL_ID,PRINCIPAL_TENANT_ID, " +
            "POLICY_URL,STATE,PII_CONTROLLER) values (?,?,?,?,?,?,?,?,?,?,?)";

    public static final String DELETE_RECEIPT_SQL = "DELETE FROM CM_RECEIPT WHERE CONSENT_RECEIPT_ID = ?";

    public static final String DELETE_RECEIPT_SP_ASSOC_SQL = "DELETE FROM CM_RECEIPT_SP_ASSOC WHERE CONSENT_RECEIPT_ID" +
            " = ?";

    public static final String DELETE_SP_TO_PURPOSE_ASSOC_SQL = "DELETE FROM CM_SP_PURPOSE_ASSOC WHERE " +
            "RECEIPT_SP_ASSOC = ?";

    public static final String DELETE_SP_PURPOSE_TO_PURPOSE_CAT_ASSOC_SQL = "DELETE FROM CM_SP_PURPOSE_PURPOSE_CAT_ASSC WHERE " +
            "SP_PURPOSE_ASSOC_ID = ?";

    public static final String DELETE_SP_PURPOSE_TO_PII_CAT_ASSOC_SQL = "DELETE FROM CM_SP_PURPOSE_PII_CAT_ASSOC WHERE " +
            "SP_PURPOSE_ASSOC_ID = ?";

    public static final String DELETE_RECEIPT_PROPERTIES_SQL =  "DELETE FROM CM_CONSENT_RECEIPT_PROPERTY WHERE " +
            "CONSENT_RECEIPT_ID = ?";

    public static final String INSERT_RECEIPT_SP_ASSOC_SQL = "INSERT INTO CM_RECEIPT_SP_ASSOC (CONSENT_RECEIPT_ID, SP_NAME," +
            "SP_TENANT_ID,SP_DISPLAY_NAME,SP_DESCRIPTION) VALUES (?,?,?,?,?)";

    public static final String INSERT_SP_TO_PURPOSE_ASSOC_SQL = "INSERT INTO CM_SP_PURPOSE_ASSOC (RECEIPT_SP_ASSOC," +
            "PURPOSE_ID,CONSENT_TYPE,IS_PRIMARY_PURPOSE,TERMINATION,THIRD_PARTY_DISCLOSURE,THIRD_PARTY_NAME) VALUES " +
            "(?,?,?,?,?,?,?)";

    public static final String INSERT_SP_PURPOSE_TO_PURPOSE_CAT_ASSOC_SQL = "INSERT INTO CM_SP_PURPOSE_PURPOSE_CAT_ASSC " +
            "(SP_PURPOSE_ASSOC_ID, PURPOSE_CATEGORY_ID) VALUES (?,?)";

    public static final String INSERT_SP_PURPOSE_TO_PII_CAT_ASSOC_SQL = "INSERT INTO CM_SP_PURPOSE_PII_CAT_ASSOC " +
            "(SP_PURPOSE_ASSOC_ID, PII_CATEGORY_ID, VALIDITY) VALUES (?,?,?)";

    public static final String INSERT_RECEIPT_PROPERTIES_SQL = "INSERT INTO CM_CONSENT_RECEIPT_PROPERTY " +
            "(CONSENT_RECEIPT_ID,NAME,VALUE) VALUES (?,?,?)";

    public static final String GET_RECEIPT_SQL = "SELECT VERSION,JURISDICTION,CONSENT_TIMESTAMP,COLLECTION_METHOD," +
            "LANGUAGE,PII_PRINCIPAL_ID,PRINCIPAL_TENANT_ID,POLICY_URL,STATE,PII_CONTROLLER FROM CM_RECEIPT WHERE " +
            "CONSENT_RECEIPT_ID =?";

    public static final String GET_RECEIPT_BASIC_SQL = "SELECT PII_PRINCIPAL_ID FROM CM_RECEIPT" +
            " WHERE CONSENT_RECEIPT_ID =? AND PII_PRINCIPAL_ID =? AND PRINCIPAL_TENANT_ID = ?";

    public static final String GET_RECEIPT_SP_SQL = "SELECT ID,SP_NAME,SP_TENANT_ID,SP_DISPLAY_NAME,SP_DESCRIPTION  " +
            "FROM CM_RECEIPT_SP_ASSOC WHERE CONSENT_RECEIPT_ID =?";

    public static final String GET_SP_PURPOSE_SQL = "SELECT SP.ID,SP.CONSENT_TYPE,SP.IS_PRIMARY_PURPOSE," +
            "SP.TERMINATION,SP.THIRD_PARTY_DISCLOSURE,SP.THIRD_PARTY_NAME,P.NAME,P.DESCRIPTION,P.PURPOSE_ID,P.VERSION" +
            " FROM " +
            "CM_SP_PURPOSE_ASSOC SP INNER JOIN  CM_PURPOSE P ON SP.PURPOSE_ID = P.ID WHERE RECEIPT_SP_ASSOC =?";

    public static final String GET_PURPOSE_CAT_SQL = "SELECT NAME FROM CM_SP_PURPOSE_PURPOSE_CAT_ASSC SPC " +
            "INNER JOIN  CM_PURPOSE_CATEGORY PC ON SPC.PURPOSE_CATEGORY_ID = PC.ID WHERE SPC.SP_PURPOSE_ASSOC_ID =?";

    public static final String GET_PII_CAT_SQL = "SELECT PC.NAME,PC.IS_SENSITIVE,SPC.VALIDITY,PC.ID,PC.DISPLAY_NAME FROM " +
            "CM_SP_PURPOSE_PII_CAT_ASSOC SPC INNER JOIN  CM_PII_CATEGORY PC ON SPC.PII_CATEGORY_ID = PC.ID WHERE" +
            " SPC.SP_PURPOSE_ASSOC_ID =?";

    public static final String SEARCH_RECEIPT_SQL = "SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R.PII_PRINCIPAL_ID, R" +
            ".PRINCIPAL_TENANT_ID, R.STATE,RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM CM_RECEIPT R INNER JOIN " +
            "CM_RECEIPT_SP_ASSOC RS ON R.CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID LIKE ? AND " +
            "PRINCIPAL_TENANT_ID=? AND " +
            "SP_NAME LIKE ? AND SP_TENANT_ID = ? AND STATE LIKE ? ORDER BY ID  ASC LIMIT ? OFFSET ?";

    public static final String SEARCH_RECEIPT_SQL_DB2 = "SELECT CONSENT_RECEIPT_ID, LANGUAGE, PII_PRINCIPAL_ID, " +
            "PRINCIPAL_TENANT_ID,STATE,SP_DISPLAY_NAME,SP_DESCRIPTION FROM (SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R" +
            ".PII_PRINCIPAL_ID, R.PRINCIPAL_TENANT_ID, RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION,R.STATE,ROW_NUMBER() OVER" +
            "( ORDER BY R.CONSENT_RECEIPT_ID) AS ROWNUMBER FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON  R" +
            ".CONSENT_RECEIPT_ID = RS.CONSENT_RECEIPT_ID WHERE R.PII_PRINCIPAL_ID LIKE ? AND PRINCIPAL_TENANT_ID = ? " +
            "AND RS.SP_NAME LIKE ? AND RS" +
            ".SP_TENANT_ID LIKE ? AND R.STATE LIKE ?) AS X WHERE ROWNUMBER BETWEEN ? AND ?";

    public static final String SEARCH_RECEIPT_SQL_INFORMIX = "SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R" +
            ".PII_PRINCIPAL_ID, R.PRINCIPAL_TENANT_ID, R.STATE,RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM CM_RECEIPT R" +
            " INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R.CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID " +
            "LIKE ? AND PRINCIPAL_TENANT_ID =? AND SP_NAME LIKE ? AND SP_TENANT_ID LIKE ? AND STATE LIKE ? ORDER " +
            "BY ID  ASC LIMIT ? OFFSET ?";

    public static final String SEARCH_RECEIPT_SQL_ORACLE = "SELECT CONSENT_RECEIPT_ID, LANGUAGE," +
            "PII_PRINCIPAL_ID,  PRINCIPAL_TENANT_ID, STATE,SP_DISPLAY_NAME,SP_DESCRIPTION FROM (SELECT ROWNUM RNUM,A" +
            ".* FROM( SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE,R.PII_PRINCIPAL_ID,R.PRINCIPAL_TENANT_ID, R.STATE,RS" +
            ".SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R" +
            ".CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID LIKE ? AND PRINCIPAL_TENANT_ID = ? " +
            "AND SP_NAME LIKE ? AND " +
            "SP_TENANT_ID LIKE ? AND STATE LIKE ? ORDER BY R.CONSENT_RECEIPT_ID ) A WHERE  ROWNUM <= ? ) WHERE  RNUM " +
            "> ?";

    public static final String SEARCH_RECEIPT_SQL_MSSQL = "SELECT CONSENT_RECEIPT_ID, LANGUAGE, PII_PRINCIPAL_ID, " +
            "PRINCIPAL_TENANT_ID, STATE, " +
            "SP_DISPLAY_NAME, SP_DESCRIPTION FROM (SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R.PII_PRINCIPAL_ID, R" +
            ".PRINCIPAL_TENANT_ID, R.STATE, RS.SP_DISPLAY_NAME, RS.SP_DESCRIPTION, ROW_NUMBER() OVER ( ORDER BY R" +
            ".CONSENT_RECEIPT_ID) AS ROWNUM FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R" +
            ".CONSENT_RECEIPT_ID = RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID LIKE ? AND PRINCIPAL_TENANT_ID = ? " +
            "AND SP_NAME LIKE ? AND " +
            "SP_TENANT_ID LIKE ? AND STATE LIKE ?) AS RES WHERE RES.ROWNUM BETWEEN ? AND ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_SP_TENANT = "SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R" +
            ".PII_PRINCIPAL_ID, R.PRINCIPAL_TENANT_ID, R.STATE,RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM CM_RECEIPT R" +
            " INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R.CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID " +
            "LIKE ? AND PRINCIPAL_TENANT_ID =? AND SP_NAME LIKE ? AND STATE LIKE ? ORDER BY ID  ASC LIMIT ? OFFSET ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_SP_TENANT_DB2 = "SELECT CONSENT_RECEIPT_ID, LANGUAGE, " +
            "PII_PRINCIPAL_ID, PRINCIPAL_TENANT_ID,STATE,SP_DISPLAY_NAME,SP_DESCRIPTION  FROM (SELECT R" +
            ".CONSENT_RECEIPT_ID, R.LANGUAGE, R.PII_PRINCIPAL_ID, R.PRINCIPAL_TENANT_ID, R.STATE,SP_DISPLAY_NAME," +
            "SP_DESCRIPTION, ROW_NUMBER() OVER( ORDER BY R.CONSENT_RECEIPT_ID) AS ROWNUMBER FROM CM_RECEIPT R INNER " +
            "JOIN CM_RECEIPT_SP_ASSOC RS ON R.CONSENT_RECEIPT_ID = RS.CONSENT_RECEIPT_ID WHERE R.PII_PRINCIPAL_ID " +
            "LIKE ? AND PRINCIPAL_TENANT_ID=? AND RS.SP_NAME LIKE ? AND R.STATE LIKE ?) AS X WHERE ROWNUMBER BETWEEN " +
            "? AND ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_SP_TENANT_INFORMIX = "SELECT R.CONSENT_RECEIPT_ID, R" +
            ".LANGUAGE, R.PII_PRINCIPAL_ID, R.PRINCIPAL_TENANT_ID, R.STATE,RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM " +
            "CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R.CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE " +
            "PII_PRINCIPAL_ID LIKE ? AND PRINCIPAL_TENANT_ID = ? AND SP_NAME LIKE ? AND STATE LIKE ? ORDER BY ID  ASC" +
            " LIMIT ? OFFSET ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_SP_TENANT_ORACLE = "SELECT CONSENT_RECEIPT_ID, LANGUAGE," +
            "PII_PRINCIPAL_ID,  PRINCIPAL_TENANT_ID, STATE,SP_DISPLAY_NAME,SP_DESCRIPTION FROM (SELECT ROWNUM RNUM,A" +
            ".* FROM( SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE,R.PII_PRINCIPAL_ID,R.PRINCIPAL_TENANT_ID, R.STATE,RS" +
            ".SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R" +
            ".CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID LIKE ? AND PRINCIPAL_TENANT_ID = ? AND " +
            "SP_NAME LIKE ? AND STATE " +
            "LIKE ? ORDER BY   R.CONSENT_RECEIPT_ID ) A WHERE  ROWNUM <= ? ) WHERE  RNUM > ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_SP_TENANT_MSSQL = "SELECT CONSENT_RECEIPT_ID, LANGUAGE, " +
            "PII_PRINCIPAL_ID, PRINCIPAL_TENANT_ID, STATE, " +
            "SP_DISPLAY_NAME, SP_DESCRIPTION FROM (SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R.PII_PRINCIPAL_ID, R" +
            ".PRINCIPAL_TENANT_ID, R.STATE, RS.SP_DISPLAY_NAME, RS.SP_DESCRIPTION, ROW_NUMBER() OVER ( ORDER BY R" +
            ".CONSENT_RECEIPT_ID) AS ROWNUM FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R" +
            ".CONSENT_RECEIPT_ID = RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID LIKE ? AND PRINCIPAL_TENANT_ID=? AND " +
            "SP_NAME LIKE ? AND " +
            "STATE LIKE ?) AS RES WHERE RES.ROWNUM BETWEEN ? AND ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_PRINCIPLE_TENANT = "SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, " +
            "R.PII_PRINCIPAL_ID, R.PRINCIPAL_TENANT_ID, R.STATE,RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM " +
            "CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R.CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE " +
            "PII_PRINCIPAL_ID LIKE ? AND SP_NAME LIKE ? AND SP_TENANT_ID = ? AND STATE LIKE ? ORDER BY ID  " +
            "ASC LIMIT ? OFFSET ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_PRINCIPLE_TENANT_DB2 =
            "SELECT CONSENT_RECEIPT_ID, LANGUAGE, PII_PRINCIPAL_ID, " +
            "PRINCIPAL_TENANT_ID,STATE,SP_DISPLAY_NAME,SP_DESCRIPTION FROM (SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R" +
            ".PII_PRINCIPAL_ID, R.PRINCIPAL_TENANT_ID, RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION,R.STATE,ROW_NUMBER() OVER" +
            "( ORDER BY R.CONSENT_RECEIPT_ID) AS ROWNUMBER FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON  R" +
            ".CONSENT_RECEIPT_ID = RS.CONSENT_RECEIPT_ID WHERE R.PII_PRINCIPAL_ID LIKE ? AND RS.SP_NAME LIKE ? AND RS" +
            ".SP_TENANT_ID LIKE ? AND R.STATE LIKE ?) AS X WHERE ROWNUMBER BETWEEN ? AND ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_PRINCIPLE_TENANT_INFORMIX =
            "SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R" +
            ".PII_PRINCIPAL_ID, R.PRINCIPAL_TENANT_ID, R.STATE,RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM CM_RECEIPT R" +
            " INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R.CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID " +
            "LIKE ? AND SP_NAME LIKE ? AND SP_TENANT_ID LIKE ? AND STATE LIKE ? ORDER " +
            "BY ID  ASC LIMIT ? OFFSET ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_PRINCIPLE_TENANT_ORACLE = "SELECT CONSENT_RECEIPT_ID, LANGUAGE," +
            "PII_PRINCIPAL_ID,  PRINCIPAL_TENANT_ID, STATE,SP_DISPLAY_NAME,SP_DESCRIPTION FROM (SELECT ROWNUM RNUM,A" +
            ".* FROM( SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE,R.PII_PRINCIPAL_ID,R.PRINCIPAL_TENANT_ID, R.STATE,RS" +
            ".SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R" +
            ".CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID LIKE ? AND SP_NAME LIKE ? AND " +
            "SP_TENANT_ID LIKE ? AND STATE LIKE ? ORDER BY R.CONSENT_RECEIPT_ID ) A WHERE  ROWNUM <= ? ) WHERE  RNUM " +
            "> ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_PRINCIPLE_TENANT_MSSQL =
            "SELECT CONSENT_RECEIPT_ID, LANGUAGE, PII_PRINCIPAL_ID, PRINCIPAL_TENANT_ID, STATE, " +
            "SP_DISPLAY_NAME, SP_DESCRIPTION FROM (SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R.PII_PRINCIPAL_ID, R" +
            ".PRINCIPAL_TENANT_ID, R.STATE, RS.SP_DISPLAY_NAME, RS.SP_DESCRIPTION, ROW_NUMBER() OVER ( ORDER BY R" +
            ".CONSENT_RECEIPT_ID) AS ROWNUM FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R" +
            ".CONSENT_RECEIPT_ID = RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID LIKE ? AND SP_NAME LIKE ? AND " +
            "SP_TENANT_ID LIKE ? AND STATE LIKE ?) AS RES WHERE RES.ROWNUM BETWEEN ? AND ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_SP_TENANT_AND_PRINCIPLE_TENANT = "SELECT " +
            "R.CONSENT_RECEIPT_ID, R.LANGUAGE, R.PII_PRINCIPAL_ID, R" +
            ".PRINCIPAL_TENANT_ID, R.STATE,RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM CM_RECEIPT R INNER JOIN " +
            "CM_RECEIPT_SP_ASSOC RS ON R.CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID LIKE ? AND " +
            "SP_NAME LIKE ? AND STATE LIKE ? ORDER BY ID  ASC LIMIT ? OFFSET ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_SP_TENANT_AND_PRINCIPLE_TENANT_DB2 =
            "SELECT CONSENT_RECEIPT_ID, LANGUAGE, PII_PRINCIPAL_ID, " +
            "PRINCIPAL_TENANT_ID,STATE,SP_DISPLAY_NAME,SP_DESCRIPTION FROM (SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R" +
            ".PII_PRINCIPAL_ID, R.PRINCIPAL_TENANT_ID, RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION,R.STATE,ROW_NUMBER() OVER" +
            "( ORDER BY R.CONSENT_RECEIPT_ID) AS ROWNUMBER FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON  R" +
            ".CONSENT_RECEIPT_ID = RS.CONSENT_RECEIPT_ID WHERE R.PII_PRINCIPAL_ID LIKE ? AND RS.SP_NAME LIKE ? AND " +
            "R.STATE LIKE ?) AS X WHERE ROWNUMBER BETWEEN ? AND ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_SP_TENANT_AND_PRINCIPLE_TENANT_INFORMIX = "SELECT " +
            "R.CONSENT_RECEIPT_ID, R.LANGUAGE, R.PII_PRINCIPAL_ID, R.PRINCIPAL_TENANT_ID, " +
            "R.STATE,RS.SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS " +
            "ON R.CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID " +
            "LIKE ? AND SP_NAME LIKE ? AND STATE LIKE ? ORDER BY ID  ASC LIMIT ? OFFSET ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_SP_TENANT_AND_PRINCIPLE_TENANT_ORACLE =
            "SELECT CONSENT_RECEIPT_ID, LANGUAGE," +
            "PII_PRINCIPAL_ID,  PRINCIPAL_TENANT_ID, STATE,SP_DISPLAY_NAME,SP_DESCRIPTION FROM (SELECT ROWNUM RNUM,A" +
            ".* FROM( SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE,R.PII_PRINCIPAL_ID,R.PRINCIPAL_TENANT_ID, R.STATE,RS" +
            ".SP_DISPLAY_NAME,RS.SP_DESCRIPTION FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R" +
            ".CONSENT_RECEIPT_ID=RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID LIKE ? AND SP_NAME LIKE ? AND " +
                    "STATE LIKE ? ORDER BY R.CONSENT_RECEIPT_ID ) A WHERE  ROWNUM <= ? ) WHERE  RNUM > ?";

    public static final String SEARCH_RECEIPT_SQL_WITHOUT_SP_TENANT_AND_PRINCIPLE_TENANT_MSSQL = "SELECT " +
            "CONSENT_RECEIPT_ID, LANGUAGE, PII_PRINCIPAL_ID, " + "PRINCIPAL_TENANT_ID, STATE, " +
            "SP_DISPLAY_NAME, SP_DESCRIPTION FROM (SELECT R.CONSENT_RECEIPT_ID, R.LANGUAGE, R.PII_PRINCIPAL_ID, R" +
            ".PRINCIPAL_TENANT_ID, R.STATE, RS.SP_DISPLAY_NAME, RS.SP_DESCRIPTION, ROW_NUMBER() OVER ( ORDER BY R" +
            ".CONSENT_RECEIPT_ID) AS ROWNUM FROM CM_RECEIPT R INNER JOIN CM_RECEIPT_SP_ASSOC RS ON R" +
            ".CONSENT_RECEIPT_ID = RS.CONSENT_RECEIPT_ID WHERE PII_PRINCIPAL_ID LIKE ? AND SP_NAME LIKE ? " +
            "AND STATE LIKE ?) AS RES WHERE RES.ROWNUM BETWEEN ? AND ?";

    public static final String INSERT_RECEIPT_PURPOSE_PII_ASSOC_SQL = "INSERT INTO CM_PURPOSE_PII_CAT_ASSOC " +
            "(PURPOSE_ID, CM_PII_CATEGORY_ID, IS_MANDATORY) VALUES (?,?,?)";

    public static final String REVOKE_RECEIPT_SQL = "UPDATE CM_RECEIPT SET STATE = ? WHERE CONSENT_RECEIPT_ID = ?";

    public static final String GET_PURPOSE_PII_CAT_SQL = "SELECT CM_PII_CATEGORY_ID, IS_MANDATORY FROM " +
                                                         "CM_PURPOSE_PII_CAT_ASSOC WHERE PURPOSE_ID = ?";
    public static final String GET_ACTIVE_RECEIPTS_SQL = "SELECT R.CONSENT_RECEIPT_ID FROM CM_RECEIPT R INNER JOIN " +
            "CM_RECEIPT_SP_ASSOC RA ON R.CONSENT_RECEIPT_ID=RA.CONSENT_RECEIPT_ID WHERE R.PII_PRINCIPAL_ID= ? AND RA" +
            ".SP_NAME=? AND R.PRINCIPAL_TENANT_ID=? AND RA.SP_TENANT_ID=? AND R.STATE='ACTIVE'";

    public static final String GET_RECEIPT_COUNT_ASSOCIATED_WITH_PURPOSE = "SELECT COUNT(*) FROM " +
            "CM_SP_PURPOSE_ASSOC WHERE PURPOSE_ID=?";
    public static final String GET_PURPOSE_COUNT_ASSOCIATED_WITH_PII_CATEGORY = "SELECT COUNT(*) FROM " +
            "CM_PURPOSE_PII_CAT_ASSOC WHERE CM_PII_CATEGORY_ID=?";
    public static final String GET_SP_PURPOSE_COUNT_ASSOCIATED_WITH_PII_CATEGORY = "SELECT COUNT(*) FROM " +
            "CM_SP_PURPOSE_PII_CAT_ASSOC WHERE PII_CATEGORY_ID=?";
}
